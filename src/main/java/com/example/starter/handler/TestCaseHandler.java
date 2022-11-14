package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.TestCaseDAO;
import com.example.starter.dto.TestCase;
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
                Integer sectionId = json.getInteger("section-id");
                Integer templateId = json.getInteger("template-id");
                Integer typeId = json.getInteger("type-id");
                Integer priorityId = json.getInteger("priority-id");
                Integer estimate = json.getInteger("estimate");
                String preconditions = json.getString("preconditions");
                String steps = json.getString("steps");
                String expectedResult = json.getString("expected-result");
                Integer userId = json.getInteger("user-id");
                Integer projectId = json.getInteger("project-id");
                TestCase testCase = new TestCase(title, sectionId, templateId, typeId, priorityId, estimate, preconditions, steps, expectedResult, userId, projectId);
                TestCaseDAO.addTestCase(testCase);
                Util.sendResponse(rc, 200, "successfully created TestCase");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testcase handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
