package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.ProjectDAO;
import com.example.starter.dto.Project;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProjectHandler {
    private ProjectHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ProjectHandler.class.getName());

    public static void getProjects(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Project> response = ProjectDAO.getProjects();
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void addProject(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String announcement = json.getString("announcement");
                Project project = new Project(name, announcement);
                ProjectDAO.addProject(project);
                Util.sendResponse(rc, 200, "successfully created project");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
