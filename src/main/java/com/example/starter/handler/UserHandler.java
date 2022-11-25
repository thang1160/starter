package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Users;
import com.example.starter.service.UsersService;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {
    private UserHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(UserHandler.class.getName());

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Users> response = UsersService.findAll();
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
