package com.example.starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.model.MilestoneDTO;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class MilestoneDAO extends Db {
    private static final Logger _LOGGER = Logger.getLogger(MilestoneDAO.class.getName());

    public static void main(String[] args) {
        _LOGGER.log(Level.INFO, "test milestone {0}", getMilestonesByProjectId(1));
    }

    public static JsonArray getMilestonesByProjectId(int projectId) {
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetMilestonesByProjectId] ?;");
            ps.setInt(1, projectId);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    JsonObject m = new JsonObject();
                    m.put("id", rs.getInt(1));
                    m.put("name", rs.getString(2));
                    m.put("due-date", rs.getString(3));
                    m.put("status", rs.getInt(4));
                    response.add(m);
                }
            }
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "get projects from database failed", ex);
        } finally {
            close(rs, ps, conn);
        }
        return response;
    }

    public static void addMilestone(MilestoneDTO milestone) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Milestones(milestone_Name,description,start_date,end_date,is_started,project_id,completed_on)\n"
                    + "VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, milestone.getName());
            ps.setString(2, milestone.getDescription());
            ps.setObject(3, milestone.getStartDate());
            ps.setObject(4, milestone.getEndDate());
            ps.setBoolean(5, milestone.isStarted());
            ps.setInt(6, milestone.getProjectId());
            ps.setObject(7, milestone.getCompleteOn());
            ps.executeUpdate();
        } finally {
            close(conn, ps);
        }
    }
}
