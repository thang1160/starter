package com.example.starter.handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.ReportDAO;
import com.example.starter.dto.Report;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
public class ReportHandler {
    private ReportHandler() {}
    private static final Logger _LOGGER = Logger.getLogger(ReportHandler.class.getName());
    public static void addReport(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String description = json.getString("description");
                Integer projectId = json.getInteger("projectId");
                Integer created_by = json.getInteger("created_by");
                Integer milestoneId = json.getInteger("milestoneId");
                Integer planId = json.getInteger("planId");
                Report report = new Report(name, description, projectId, created_by, milestoneId, planId);
                ReportDAO.addReport(report);
                Util.sendResponse(rc, 200, "successfully created Report");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add report handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
