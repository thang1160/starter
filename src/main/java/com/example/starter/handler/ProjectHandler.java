package com.example.starter.handler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.MilestoneDAO;
import com.example.starter.dao.TestCaseDAO;
import com.example.starter.dao.TestRunDAO;
import com.example.starter.entity.Projects;
import com.example.starter.service.ProjectsService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProjectHandler {
    private ProjectHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ProjectHandler.class.getName());

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Projects> response = ProjectsService.findAll();
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
                Projects project = rc.body().asPojo(Projects.class);
                ProjectsService.create(project);
                Util.sendResponse(rc, 200, "successfully created project");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void getProjectById(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                int projectId = Integer.parseInt(rc.pathParam("projectId"));
                CompletableFuture<JsonArray> milestones = CompletableFuture.supplyAsync(() -> MilestoneDAO.getMilestonesByProjectId(projectId));
                CompletableFuture<JsonArray> testruns = CompletableFuture.supplyAsync(() -> TestRunDAO.getTestRunsByProjectId(projectId));
                CompletableFuture<JsonArray> testcases = CompletableFuture.supplyAsync(() -> TestCaseDAO.getTestCasesByProjectId(projectId));
                CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(milestones, testruns, testcases);
                combinedFuture.get();
                JsonObject response = new JsonObject()
                        .put("milestones", milestones.get())
                        .put("test-runes", testruns.get())
                        .put("test-cases", testcases.get());
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }, false, null);
    }
}
