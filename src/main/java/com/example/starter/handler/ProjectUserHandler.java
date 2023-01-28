package com.example.starter.handler;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.ProjectUser;
import com.example.starter.service.BaseService;
import com.example.starter.service.ProjectUserService;
import io.vertx.ext.web.RoutingContext;

public class ProjectUserHandler {
    private ProjectUserHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ProjectUserHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                ProjectUser projectUser = rc.body().asPojo(ProjectUser.class);
                BaseService.create(projectUser);
                Util.sendResponse(rc, 200, projectUser);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "create project user handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void delete(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectUserId");
            try {
                Long projectUserId = Long.parseLong(stringId);
                int deletedCount = ProjectUserService.delete(projectUserId);
                Util.sendResponse(rc, 200, Map.of("deletedCount", deletedCount));
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find project user handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<ProjectUser> response = ProjectUserService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get report handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

}
