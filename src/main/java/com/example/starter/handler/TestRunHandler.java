package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.TestRun;
import com.example.starter.service.BaseService;
import io.vertx.ext.web.RoutingContext;

public class TestRunHandler {
    private TestRunHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestRunHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                TestRun testRun = rc.body().asPojo(TestRun.class);
                BaseService.create(testRun);
                Util.sendResponse(rc, 200, "successfully created TestRun");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "create TestRun handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
