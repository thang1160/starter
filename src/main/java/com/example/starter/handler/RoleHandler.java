package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Roles;
import com.example.starter.service.RolesService;
import io.vertx.ext.web.RoutingContext;

public class RoleHandler {
    private RoleHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(RoleHandler.class.getName());

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Roles> response = RolesService.findAll();
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get users handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

}
