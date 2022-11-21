package com.example.starter.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Milestones;
import com.example.starter.entity.Projects;
import com.example.starter.entity.TestCase;
import com.example.starter.entity.TestRun;
import com.example.starter.service.BaseService;
import com.example.starter.service.MilestonesService;
import com.example.starter.service.ProjectsService;
import com.example.starter.service.TestCaseService;
import com.example.starter.service.TestRunService;
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
                BaseService.create(project);
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
                CompletableFuture<List<Milestones>> milestones = CompletableFuture.supplyAsync(() -> MilestonesService.findAllByProjectId(projectId));
                // CompletableFuture<List<TestRun>> testruns = CompletableFuture.supplyAsync(() -> TestRunService.findAllByProjectId(projectId));
                // CompletableFuture<List<TestCase>> testcases = CompletableFuture.supplyAsync(() -> TestCaseService.findAllByProjectId(projectId));
                // CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(milestones, testruns, testcases);
                CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(milestones);
                combinedFuture.get();
                // Map<String, Object> response = Map.of("milestones", milestones.get(),"test-runes", testruns.get(),"test-cases", testcases.get());
                Map<String, Object> response = Map.of("milestones", milestones.get());
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
