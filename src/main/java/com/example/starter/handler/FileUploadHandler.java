package com.example.starter.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.FileUpload;
import com.example.starter.service.BaseService;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

public class FileUploadHandler {
    private static final Logger _LOGGER = Logger.getLogger(FileUploadHandler.class.getName());

    public static void findByFileUploadId(RoutingContext rc, WebClient client) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("fileUploadId");
            try {
                Long fileUploadId = Long.parseLong(stringId);
                FileUpload fileUpload = BaseService.findById(FileUpload.class, fileUploadId);
                client.get(80, "localhost", "/tms-upload/" + fileUpload.getUploadedFileName()).send()
                        .onSuccess(res -> {
                            System.out.println("Received response with status code" + res.statusCode());
                            rc.response().putHeader(io.vertx.core.http.HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileUpload.getFileName());
                            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, fileUpload.getContentType());
                            rc.response().end(res.bodyAsBuffer());
                        })
                        .onFailure(err -> {
                            System.out.println("Something went wrong " + err.getMessage());
                            Util.sendResponse(rc, 500, err.getMessage());
                        });

                // Util.sendResponse(rc, 200, fileUpload);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get milestone handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
