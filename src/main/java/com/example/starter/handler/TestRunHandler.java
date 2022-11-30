package com.example.starter.handler;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Result;
import com.example.starter.entity.TestCase;
import com.example.starter.entity.TestRun;
import com.example.starter.service.BaseService;
import com.example.starter.service.TestCaseService;
import com.example.starter.service.TestRunService;
import io.vertx.ext.web.RoutingContext;

public class TestRunHandler {
    private TestRunHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestRunHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                TestRun testRun = rc.body().asPojo(TestRun.class);
                BaseService.create(testRun);
                if (Optional.ofNullable(testRun.getIncludeAll()).orElse(false)) {
                    List<TestCase> testCases = TestCaseService.findAllByProjectId(testRun.getProjectId());
                    Result[] results = testCases.stream().map(testCase -> {
                        Result r = new Result();
                        r.setCaseId(testCase.getCaseId());
                        r.setRunId(testRun.getRunId());
                        return r;
                    }).toArray(Result[]::new);
                    BaseService.createBatch(results);
                }
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
}
