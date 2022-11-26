package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Sections;
import com.example.starter.service.BaseService;
import com.example.starter.service.SectionsService;
import io.vertx.ext.web.RoutingContext;

public class SectionHandler {
    private SectionHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(SectionHandler.class.getName());

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<Sections> response = SectionsService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get section handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Sections section = rc.body().asPojo(Sections.class);
                BaseService.create(section);
                Util.sendResponse(rc, 200, "successfully created section");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add section handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
