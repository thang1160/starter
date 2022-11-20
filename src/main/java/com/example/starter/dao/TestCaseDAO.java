package com.example.starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.model.TestCase;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class TestCaseDAO extends Db {
    private static final Logger _LOGGER = Logger.getLogger(TestCaseDAO.class.getName());

    public static void main(String[] args) {
        _LOGGER.log(Level.INFO, "test testcase {0}", getTestCasesByProjectId(1));
    }

    public static JsonArray getTestCasesByProjectId(int projectId) {
        JsonArray response = new JsonArray();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("exec [GetTestCasesByProjectId] ?;");
            ps.setInt(1, projectId);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    JsonObject t = new JsonObject();
                    t.put("id", rs.getInt(1));
                    t.put("name", rs.getString(2));
                    t.put("create-date", rs.getString(3));
                    t.put("create-by", rs.getString(4));
                    t.put("status", rs.getInt(5));
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

    public static void addTestCase(TestCase testCase) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("insert into testcase (case_Name, estimate, section_id, priority_id, user_id, status_id, is_deleted, created_on, updated_on, updated_by, project_id)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?, ?)");
            ps.setString(1, testCase.getTitle());
            ps.setInt(2, testCase.getEstimate());
            ps.setInt(3, testCase.getSectionId());
            ps.setInt(4, testCase.getPriorityId());
            ps.setInt(5, testCase.getUserId());
            ps.setInt(6, testCase.getStatusId());
            ps.setInt(7, testCase.isDeleted() ? 1 : 0);
            ps.setInt(8, testCase.getUpdatedBy());
            ps.setInt(9, testCase.getProjectId());
            ps.executeUpdate();
        } finally {
            close(conn, ps);
        }
    }
}
