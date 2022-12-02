package com.example.starter.handler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Result;
import com.example.starter.service.ResultService;
import io.vertx.ext.web.RoutingContext;

public class ResultHandler {
    private ResultHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ResultHandler.class.getName());

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

    public static void findAllByTestRunId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("testRunId");
            try {
                int testRunId = Integer.parseInt(stringId);
                List<Result> response = ResultService.findAllByTestRunId(testRunId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get result handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
