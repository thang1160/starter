package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Priorities;
import com.example.starter.service.PrioritiesService;
import io.vertx.ext.web.RoutingContext;

public class PriorityHandler {
    private PriorityHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(PriorityHandler.class.getName());

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Priorities> response = PrioritiesService.findAll();
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get Priority handler failed:{0}", new Object[] {e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
