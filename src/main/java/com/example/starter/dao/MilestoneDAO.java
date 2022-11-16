package com.example.starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.starter.dto.Milestone;


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
                    m.put("id", rs.getInt("id"));
                    m.put("name", rs.getString("name"));
                    m.put("description", rs.getString("description"));
                    m.put("start_date", rs.getDate("start_date"));
                    m.put("end_date", rs.getDate("end_date"));
                    m.put("is_started", rs.getBoolean("is_started"));
                    m.put("project_id", rs.getInt("project_id"));
                    m.put("is_completed", rs.getBoolean("is_completed"));
                    response.add(m);
                }
            }
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "get Milestones from database failed", ex);
        } finally {
            close(rs, ps, conn);
        }
        return response;

        //Add Milestones
    }
    public static void addMilestone(Milestone milestone) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO milestone (name, description, start_date, end_date, is_started, project_id, is_completed) VALUES (?, ?, GETDATE(), GETDATE(), ?, ?, ?)");
            stmt.setString(1, milestone.getName()); 
            stmt.setString(2, milestone.getDescription());
            stmt.setDate(3, milestone.getStarDate());
            stmt.setDate(4, milestone.getEndDate());
            stmt.setBoolean(5, milestone.getIsStarted());
            stmt.setInt(6, milestone.getProject_id());
            stmt.setBoolean(7,milestone.getIsCompleted());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            close(conn, stmt);
        }
    }
}
