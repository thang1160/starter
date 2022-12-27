package com.example.starter.handler;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.example.starter.Util;
import com.example.starter.entity.FileUpload;
import com.example.starter.entity.Result;
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
                List<FileUpload> fileUploads = rc.fileUploads().stream().map(fileUpload -> {
                    String contentType = fileUpload.contentType();
                    if (!acceptMIME.contains(contentType)) {
                        throw new IllegalArgumentException("Content type is invalid: " + contentType);
                    }
                    File file = new File(fileUpload.uploadedFileName());
                    String newUploadedDir = fileUpload.uploadedFileName() + getExtensionByStringHandling(fileUpload.fileName()).map(fileName -> "." + fileName).orElse("");
                    File file2 = new File(newUploadedDir);

                    boolean success = file.renameTo(file2);
                    if (!success) {
                        throw new IllegalArgumentException("File not found: " + fileUpload.fileName());
                    }
                    String uploadedFileName = newUploadedDir.replace(Util.uploadsDirectory, "");
                    String filename = fileUpload.fileName();
                    long size = fileUpload.size();
                    return new FileUpload(uploadedFileName, filename, size, contentType);
                }).collect(Collectors.toList());
                MultiMap multiMap = rc.request().formAttributes();
                ObjectMapper mapper = DatabindCodec.mapper();
                Result result = mapper.readValue(multiMap.get("result"), Result.class);
                ResultService.update(result, fileUploads);
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

    public static void findByResultId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("resultId");
            try {
                Long resultId = Long.parseLong(stringId);
                Result result = ResultService.findById(resultId);
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find test run handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
