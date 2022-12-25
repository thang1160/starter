package com.example.starter.handler;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.example.starter.Util;
import com.example.starter.entity.FileUpload;
import com.example.starter.entity.Result;
import com.example.starter.service.BaseService;
import com.example.starter.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.MultiMap;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;

public class ResultHandler {
    private ResultHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(ResultHandler.class.getName());

    private static final Set<String> acceptMIME = Set.of("image/jpeg", "image/png");

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<FileUpload> fileUploads = rc.fileUploads().stream().map(x -> {
                    String contentType = x.contentType();
                    if (!acceptMIME.contains(contentType)) {
                        throw new IllegalArgumentException("Content type is invalid: " + contentType);
                    }
                    String uploadedFileName = x.uploadedFileName().replace(Util.uploadsDirectory, "");
                    String filename = x.fileName();
                    long size = x.size();
                    return new FileUpload(uploadedFileName, filename, size, contentType);
                }).collect(Collectors.toList());
                MultiMap multiMap = rc.request().formAttributes();
                ObjectMapper mapper = DatabindCodec.mapper();
                Result result = mapper.readValue(multiMap.get("result"), Result.class);
                ResultService.update(result);
                for (FileUpload fileUpload : fileUploads) {
                    BaseService.create(fileUpload);
                }
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
