package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.TestRunDAO;
import com.example.starter.model.TestRun;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TestRunHandler {
    private TestRunHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestRunHandler.class.getName());

    public static void addTestRun(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                Integer milestoneId = json.getInteger("milestone-id");
                Integer assignedToId = json.getInteger("assigned-to-id");
                String description = json.getString("description");
                Integer testType = json.getInteger("test-type");
                Integer userId = json.getInteger("user-id");
                Integer projectId = json.getInteger("project-id");
                TestRun testRun = new TestRun(name, milestoneId, assignedToId, description, testType, userId, projectId);
                TestRunDAO.addTestRun(testRun);
                Util.sendResponse(rc, 200, "successfully created TestRun");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add TestRun handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
