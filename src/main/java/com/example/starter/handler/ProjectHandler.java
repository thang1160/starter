package com.example.starter.handler;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Functionality;
import com.example.starter.entity.ProjectUser;
import com.example.starter.entity.Projects;
import com.example.starter.model.PagingParams;
import com.example.starter.model.PagingResponse;
import com.example.starter.service.BaseService;
import com.example.starter.service.FunctionalityService;
import com.example.starter.service.ProjectUserService;
import com.example.starter.service.ProjectsService;
import io.vertx.ext.web.RoutingContext;

public class ProjectHandler {
    private ProjectHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ProjectHandler.class.getName());

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                boolean isViewAllProject = false;
                Long userId = Long.parseLong(rc.user().principal().getString("sub"));
                List<Functionality> functionalities = FunctionalityService.findByUserId(userId);
                for (Functionality functionality : functionalities) {
                    if (functionality.getFunctionalityName().equals("VIEW_ALL_PROJECT")) {
                        isViewAllProject = true;
                        break;
                    }
                }
                PagingParams params = new PagingParams(rc.request().params());
                String projectName = rc.request().params().get("projectName");
                if (projectName == null || projectName.isBlank()) {
                    projectName = "";
                }
                PagingResponse<Projects> response = ProjectsService.findAll(params, projectName.trim(), userId, isViewAllProject);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                Projects project = rc.body().asPojo(Projects.class);
                BaseService.create(project);
                ProjectUser projectUser = new ProjectUser();
                projectUser.setProjectId(project.getProjectId());
                projectUser.setUserId(userId);
                BaseService.create(projectUser);
                Util.sendResponse(rc, 200, project);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                Long projectId = Long.parseLong(stringId);
                Projects project = BaseService.findById(Projects.class, projectId);
                Util.sendResponse(rc, 200, project);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void accessible(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                Long projectId = Long.parseLong(stringId);
                Long userId = Long.parseLong(rc.user().principal().getString("sub"));
                List<Functionality> functionalities = FunctionalityService.findByUserId(userId);
                for (Functionality functionality : functionalities) {
                    if (functionality.getFunctionalityName().equals("VIEW_ALL_PROJECT")) {
                        rc.next();
                        return;
                    }
                }
                if (ProjectUserService.isProjectMember(projectId, userId)) {
                    rc.next();
                    return;
                }
                Util.sendResponse(rc, 401, Map.of("code", 401, "message", "Unauthorized"));
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
