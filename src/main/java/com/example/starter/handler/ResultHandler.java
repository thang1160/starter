package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Result;
import com.example.starter.service.BaseService;
import com.example.starter.service.ResultService;
import io.vertx.ext.web.RoutingContext;

public class ResultHandler {
    private ResultHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ResultHandler.class.getName());

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Result[] result = rc.body().asPojo(Result[].class);
                BaseService.createBatch(result);
                Util.sendResponse(rc, 200, "successfully created result");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "create result handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Result result = rc.body().asPojo(Result.class);
                ResultService.update(result);
                Util.sendResponse(rc, 200, "successfully update result");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "update result handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
