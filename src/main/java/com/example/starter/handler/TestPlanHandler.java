package com.example.starter.handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.TestPlanDAO;
import com.example.starter.dto.TestPlan;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TestPlanHandler {
    private TestPlanHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestPlanHandler.class.getName());

    public static void addTestPlan(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("plan_name");
                String description = json.getString("description");
                Integer project_id = json.getInteger("project_id");
                Integer milestone_id = json.getInteger("milestone_id");
                TestPlan testPlan = new TestPlan(name, description, project_id, milestone_id);
                TestPlanDAO.addTestPlan(testPlan);
                Util.sendResponse(rc, 200, "successfully created TestPlan");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testplan handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
