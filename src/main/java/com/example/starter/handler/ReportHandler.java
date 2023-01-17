package com.example.starter.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.example.starter.Util;
import com.example.starter.entity.Report;
import com.example.starter.entity.Result;
import com.example.starter.entity.TestRun;
import com.example.starter.service.BaseService;
import com.example.starter.service.ReportService;
import com.example.starter.service.ResultService;
import com.example.starter.service.TestRunService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ReportHandler {

    private static final Logger _LOGGER = Logger.getLogger(ReportHandler.class.getName());

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<Report> response = ReportService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get report handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                Report report = rc.body().asPojo(Report.class);
                Set<Integer> testRunIds = report.getTestRunIds();
                JsonObject jsonData = new JsonObject();

                List<TestRun> testRuns = TestRunService.findAllByProjectId(report.getProjectId()).stream()
                        .filter(x -> testRunIds.contains(x.getRunId()))
                        .collect(Collectors.toList());
                jsonData.put("testRuns", testRuns);

                List<Result> results = new ArrayList<>();
                for (TestRun testRun : testRuns) {
                    results.addAll(ResultService.findAllByTestRunId(testRun.getRunId()));
                }
                jsonData.put("results", results);

                report.setJsonData(jsonData.toString());
                report.setCreatedBy(userId);
                BaseService.create(report);
                Util.sendResponse(rc, 200, "successfully created report");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add report handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findByReportId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("reportId");
            try {
                Long reportId = Long.parseLong(stringId);
                Report report = BaseService.findById(Report.class, reportId);
                Util.sendResponse(rc, 200, report);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find report handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
