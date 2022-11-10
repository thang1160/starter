package com.example.starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.dto.Project;

public class ProjectDAO extends Db {
    private static final Logger _LOGGER = Logger.getLogger(ProjectDAO.class.getName());

    public static List<Project> getProjects() {
        List<Project> response = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("exec GetProjects;");
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int activeTestRun = rs.getInt(3);
                    int activeMilestone = rs.getInt(4);
                    Project project = new Project(id, name, activeTestRun, activeMilestone);
                    response.add(project);
                }
            }
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, "get projects from database failed", ex);
        } finally {
            close(rs, stmt, conn);
        }
        return response;
    }

    public static void addProject(Project project) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO projects (project_Name, announcement) VALUES (?, ?)");
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getAnnouncement());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            _LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            close(conn, stmt);
        }
    }
}
