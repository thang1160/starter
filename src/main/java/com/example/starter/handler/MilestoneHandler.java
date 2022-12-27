package com.example.starter.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Activity;
import com.example.starter.entity.Milestones;
import com.example.starter.service.BaseService;
import com.example.starter.service.MilestonesService;
import io.vertx.ext.web.RoutingContext;

public class MilestoneHandler {
    private MilestoneHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(MilestoneHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                Milestones milestones = rc.body().asPojo(Milestones.class);
                milestones.setUserId(userId);
                if (milestones.getIsCompleted()) {
                    milestones.setCompletedOn(LocalDate.now());
                }
                BaseService.create(milestones);

                Activity activity = new Activity();
                activity.setAction("Created by");
                activity.setName(milestones.getMilestoneName());
                activity.setType("Milestone");
                activity.setUserId(userId);
                activity.setProjectId(milestones.getProjectId());
                activity.setTargetId(milestones.getMilestoneId());
                BaseService.create(activity);
                Util.sendResponse(rc, 200, "successfully created Milestone");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add Milestone handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<Milestones> response = MilestonesService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get milestone handler failed with project id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByMilestoneId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("milestoneId");
            try {
                Long milestoneId = Long.parseLong(stringId);
                Milestones response = BaseService.findById(Milestones.class, milestoneId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get milestone handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                Milestones milestone = rc.body().asPojo(Milestones.class);
                MilestonesService.update(milestone);
                if (milestone.getIsCompleted()) {
                    Activity activity = new Activity();
                    activity.setAction("Closed by");
                    activity.setName(milestone.getMilestoneName());
                    activity.setType("Milestone");
                    activity.setUserId(userId);
                    activity.setProjectId(milestone.getProjectId());
                    activity.setTargetId(milestone.getMilestoneId());
                    activity.setDescription("(completed)");
                    BaseService.create(activity);
                }
                Util.sendResponse(rc, 200, "successfully update milestone");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "update milestone handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
