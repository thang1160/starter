package com.example.starter.handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.MilestoneDAO;
import com.example.starter.dto.Milestone;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class MilestoneHandler {
    private MilestoneHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(MilestoneHandler.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void addMilestone(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String description = json.getString("description");
                LocalDate startDate = LocalDate.parse(json.getString("start-date"), formatter);
                LocalDate endDate = LocalDate.parse(json.getString("end-date"), formatter);
                int projectId = json.getInteger("project-id");
                boolean isComplete = json.getBoolean("is-complete");
                Milestone milestone = new Milestone(name, description, startDate, endDate, projectId, isComplete);
                MilestoneDAO.addMilestone(milestone);
                Util.sendResponse(rc, 200, "successfully created Milestone");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add Milestone handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
