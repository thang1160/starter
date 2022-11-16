package com.example.starter.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.dto.Section;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
public class SectionDAO extends Db{
    private static final Logger _LOGGER = Logger.getLogger(SectionDAO.class.getName());

    public static void main(String[] args) {
        _LOGGER.log(Level.INFO, "test section {0}", getSectionsByProjectId(1));
        
    }
    public static JsonArray getSectionsByProjectId(int project_id){
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetSectionsByProjectId] ?;");
            ps.setInt(1, project_id);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    JsonObject t = new JsonObject();
                    t.put("id", rs.getInt(1));
                    t.put("name", rs.getString(2));
                    t.put("description", rs.getString(3));
                    
                    response.add(t);
                }
            }
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "get projects from database failed", ex);
        } finally {
            close(rs, ps, conn);
        }
        return response;


    }
    public static void addSection(Section section) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("insert into section (name, description)\n"
                    + "VALUES (?, ?)");
            ps.setString(1, section.getName());
            ps.setString(2, section.getDescription());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "add section failed", ex);
            throw ex;
        } finally {
            close(ps, conn);
        }
    }
}
