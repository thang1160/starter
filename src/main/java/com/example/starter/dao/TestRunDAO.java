package com.example.starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.starter.dto.TestRun;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class TestRunDAO extends Db {
    private static final Logger _LOGGER = Logger.getLogger(TestRunDAO.class.getName());

    public static void main(String[] args) {
        _LOGGER.log(Level.INFO, "test testrun {0}", getTestRunsByProjectId(1));
    }

    public static JsonArray getTestRunsByProjectId(int projectId) {
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetTestRunsByProjectId] ?;");
            ps.setInt(1, projectId);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    JsonObject t = new JsonObject();
                    t.put("id", rs.getInt(1));
                    t.put("name", rs.getString(2));
                    t.put("create-date", rs.getString(3));
                    t.put("create-by", rs.getString(4));
                    t.put("passed-count", rs.getInt(5));
                    t.put("retest-count", rs.getInt(6));
                    t.put("failed-count", rs.getInt(7));
                    t.put("untested-count", rs.getInt(8));
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
    public static void addTestRun(TestRun testRun) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("insert into testrun (run_name, description, create_on, milestone_id, user_id, project_id,)\n"
                    + "VALUES (?, ?, GETDATE(), ?, ?, ?)");
            ps.setString(1, testRun.getName());
            ps.setString(2, testRun.getDescription());
            ps.setInt(4, testRun.getMilestoneId()); 
            ps.setInt(5, testRun.getUserId());
            ps.setInt(6, testRun.getProjectid());
            ps.executeUpdate();
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "add testrun to database failed", ex);
            throw ex;
        } finally {
            close(ps, conn);
        }
    }
        
    }

