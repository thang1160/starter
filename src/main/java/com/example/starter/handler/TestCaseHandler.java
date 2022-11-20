package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.TestCaseDAO;
import com.example.starter.model.TestCase;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TestCaseHandler {
    private TestCaseHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestCaseHandler.class.getName());

    public static void addTestCase(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String title = json.getString("title");
                Integer userId = json.getInteger("userId");
                Integer estimate = json.getInteger("estimate");
                Integer sectionId = json.getInteger("section-id");
                Integer priorityId = json.getInteger("priority-id");
                Integer milestoneId = json.getInteger("milestone-id");
                String preconditions = json.getString("preconditions");
                String steps = json.getString("steps");
                String expectedResult = json.getString("expected-result");
                Integer projectId = json.getInteger("project-id");
                TestCase testCase = new TestCase(title, estimate, sectionId, milestoneId, userId, preconditions, steps, priorityId, expectedResult, projectId);
                TestCaseDAO.addTestCase(testCase);
                Util.sendResponse(rc, 200, "successfully created TestCase");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testcase handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
