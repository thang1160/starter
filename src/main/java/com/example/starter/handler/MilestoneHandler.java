package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.MilestoneDAO;
import com.example.starter.dto.Milestone;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class MilestoneHandler {
    public MilestoneHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(MilestoneHandler.class.getName());
    public static void addMilestone(RoutingContext rc){
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String description = json.getString("description");
                Boolean isStarted = json.getBoolean("isStarted");
                Integer projectId = json.getInteger("projectId");
                Boolean isCompleted = json.getBoolean("isCompleted");
                Milestone milestone = new Milestone(name, description, projectId, isStarted, isCompleted);
                MilestoneDAO.addMilestone(milestone);
                Util.sendResponse(rc, 200, "successfully created Milestone");
                
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add milestone handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}

