package com.example.starter.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
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
                Milestones milestones = rc.body().asPojo(Milestones.class);
                if (Optional.ofNullable(milestones.getCompleted()).orElse(false)) {
                    milestones.setCompletedOn(LocalDate.now());
                } else {
                    milestones.setCompletedOn(milestones.getEndDate());
                }
                BaseService.create(milestones);
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
                _LOGGER.log(Level.SEVERE, "get milestone handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
