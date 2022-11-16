package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.TestRunDAO;
import com.example.starter.dto.TestRun;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TestRunHandler{
    private TestRunHandler(){
    }  
    private static final Logger _LOGGER = Logger.getLogger(TestRunHandler.class.getName());

    public static void addTestRun(RoutingContext rc) {
        rc.vertx().executeBlocking(future ->{
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String Description = json.getString("Description");
                Integer milestoneId = json.getInteger("milestoneId");
                Integer userId = json.getInteger("userId");
                Integer projectid = json.getInteger("projectid");
                Integer planId = json.getInteger("planId");
                boolean isCompleted = json.getBoolean("isCompleted");
                Integer assigned_to_id = json.getInteger("assigned_to_id");
                TestRun testRun = new TestRun(name, Description, milestoneId, userId, projectid, planId, isCompleted, assigned_to_id);
                TestRunDAO.addTestRun(testRun);
                Util.sendResponse(rc, 200, "successfully created TestRun");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testrun handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
        
    }
    
}