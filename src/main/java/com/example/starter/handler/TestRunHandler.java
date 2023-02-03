package com.example.starter.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.example.starter.Util;
import com.example.starter.entity.Activity;
import com.example.starter.entity.Result;
import com.example.starter.entity.TestCase;
import com.example.starter.entity.TestRun;
import com.example.starter.service.BaseService;
import com.example.starter.service.ResultService;
import com.example.starter.service.TestCaseService;
import com.example.starter.service.TestRunService;
import io.vertx.ext.web.RoutingContext;

public class TestRunHandler {
    private TestRunHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestRunHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                TestRun testRun = rc.body().asPojo(TestRun.class);
                testRun.setUserId(userId);
                Set<Result> results = null;
                if (Optional.ofNullable(testRun.getIncludeAll()).orElse(false)) {
                    List<TestCase> testCases = TestCaseService.findAllByProjectId(testRun.getProjectId());
                    results = testCases.stream().map(x -> new Result(x.getCaseId())).collect(Collectors.toSet());
                } else {
                    results = testRun.getTestRunResults();
                }
                TestRunService.create(testRun, results);

                Activity activity = new Activity();
                activity.setAction("Created by");
                activity.setName(testRun.getRunName());
                activity.setType("Test Run");
                activity.setUserId(userId);
                activity.setProjectId(testRun.getProjectId());
                activity.setTargetId(testRun.getRunId());
                BaseService.create(activity);
                Util.sendResponse(rc, 200, "successfully created TestRun");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "create TestRun handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<TestRun> response = TestRunService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get test run handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                TestRun testRun = rc.body().asPojo(TestRun.class);
                Set<Result> results = null;
                if (Optional.ofNullable(testRun.getIncludeAll()).orElse(false)) {
                    List<TestCase> testCases = TestCaseService.findAllByProjectId(testRun.getProjectId());
                    results = testCases.stream().map(x -> new Result(x.getCaseId())).collect(Collectors.toSet());
                } else {
                    results = new HashSet<>(testRun.getResults());
                }
                Set<Integer> addedCaseIds = ResultService.findAllByTestRunId(testRun.getRunId())
                        .stream().map(x -> x.getCaseId()).collect(Collectors.toSet());
                results = results.stream().filter(x -> !addedCaseIds.contains(x.getCaseId())).collect(Collectors.toSet());
                TestRunService.update(testRun, results);
                if (testRun.getIsCompleted()) {
                    Activity activity = new Activity();
                    activity.setAction("Closed by");
                    activity.setName(testRun.getRunName());
                    activity.setType("Test Run");
                    activity.setUserId(userId);
                    activity.setProjectId(testRun.getProjectId());
                    activity.setTargetId(testRun.getRunId());
                    activity.setDescription("(completed)");
                    BaseService.create(activity);
                }
                Util.sendResponse(rc, 200, "successfully update test run");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "update test run handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findByTestRunId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("testRunId");
            try {
                Long testRunId = Long.parseLong(stringId);
                TestRun testRun = BaseService.findById(TestRun.class, testRunId);
                List<Result> results = ResultService.findAllByTestRunId(testRunId);
                testRun.setResults(results);
                Util.sendResponse(rc, 200, testRun);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find test run handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByMilestoneId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("milestoneId");
            try {
                int milestoneId = Integer.parseInt(stringId);
                List<TestRun> response = TestRunService.findAllByMilestoneId(milestoneId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get test run handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
