package com.example.starter.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.PlainDocument;

import com.example.starter.dto.Report;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
public class ReportDAO  extends Db{
    private static final Logger _LOGGER = Logger.getLogger(ReportDAO.class.getName());
    public static JsonArray getReportsByProjectId(int project_id){
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetReportsByProjectId] ?;");
            ps.setInt(1, project_id);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    JsonObject t = new JsonObject();
                    t.put("id", rs.getInt(1));
                    t.put("name", rs.getString(2));
                    t.put("description", rs.getString(3));
                   
                    t.put("project_id", rs.getInt(4));
                    t.put("created_by", rs.getInt(5));
                    t.put("created_on", rs.getDate(6));
                    t.put("executed_on", rs.getInt(7));
                    t.put("milestone_id", rs.getInt(8));
                    t.put("plan_id", rs.getInt(9));
                    response.add(t);
                }
            }
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "get reports from database failed", ex);
        } finally {
            close(rs, ps, conn);
        }
        return response;
    }
    public static void addReport(Report report) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("insert into report (name, description, type, query, project_id)\n"
                    + "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, report.getName());
            ps.setString(2, report.getDescription());
            ps.setInt(3, report.getMilestoneId());

            
            ps.executeUpdate();
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "add report failed", ex);
            throw ex;
        } finally {
            close(ps, conn);
        }
    }
    
     
}
