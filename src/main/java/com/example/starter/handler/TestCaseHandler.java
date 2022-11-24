package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.TestCase;
import com.example.starter.service.BaseService;
import com.example.starter.service.TestCaseService;
import io.vertx.ext.web.RoutingContext;

public class TestCaseHandler {
    private TestCaseHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestCaseHandler.class.getName());

    public static void addTestCase(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                TestCase testCase = rc.body().asPojo(TestCase.class);
                testCase.setUpdatedBy(testCase.getUserId());
                BaseService.create(testCase);
                Util.sendResponse(rc, 200, "successfully created TestCase");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testcase handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<TestCase> response = TestCaseService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get section handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
