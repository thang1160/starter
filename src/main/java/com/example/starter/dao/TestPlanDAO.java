package com.example.starter.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.model.TestPlan;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class TestPlanDAO extends Db{
    private static final Logger _LOGGER = Logger.getLogger(TestPlanDAO.class.getName());

    public static void main(String[] args) {
        _LOGGER.log(Level.INFO, "test testplan {0}", getTestPlansByProjectId(1));
    }

    private static JsonArray getTestPlansByProjectId(int projectId) {
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetTestPlansByProjectId] ?;");
            ps.setInt(1, projectId);
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

        } catch (Exception e) {
            _LOGGER.log(Level.SEVERE, "get testplans from database failed", e);
        }finally {
            close(rs, ps, conn);
        }
        return response;
    }
    public static void addTestPlan(TestPlan testplan) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement("insert into testplan (plan_name, description, project_id, milestone_id)\n"
                    + "VALUES (?, ?, ?, ?, ?)");
        } catch (Exception e) {
            _LOGGER.log(Level.SEVERE, "add testplan to database failed", e);
        }finally{
            close(ps, connection);
        }
    }
}
