package com.example.starter.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.example.starter.Util;
import com.example.starter.entity.Priorities;
import com.example.starter.entity.Sections;
import com.example.starter.entity.TestCase;
import com.example.starter.service.BaseService;
import com.example.starter.service.PrioritiesService;
import com.example.starter.service.SectionsService;
import com.example.starter.service.TestCaseService;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

public class TestCaseHandler {
    private TestCaseHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(TestCaseHandler.class.getName());
    private static final String COMMA_DELIMITER = ",";

    public static void addTestCase(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                TestCase testCase = rc.body().asPojo(TestCase.class);
                testCase.setUserId(userId);
                testCase.setUpdatedBy(testCase.getUserId());
                BaseService.create(testCase);
                Util.sendResponse(rc, 200, "successfully created TestCase");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add testcase handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findAllByProjectId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                int projectId = Integer.parseInt(stringId);
                List<TestCase> response = TestCaseService.findAllByProjectId(projectId);
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get test case handler failed with id {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                TestCase testCase = rc.body().asPojo(TestCase.class);
                TestCaseService.update(testCase);
                Util.sendResponse(rc, 200, "successfully update test run");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "update test run handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void findByTestCaseId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("testCaseId");
            try {
                Long testCaseId = Long.parseLong(stringId);
                TestCase project = BaseService.findById(TestCase.class, testCaseId);
                Util.sendResponse(rc, 200, project);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find test case handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void deleteByTestCaseId(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("testCaseId");
            try {
                Long testCaseId = Long.parseLong(stringId);
                int deletedCount = TestCaseService.delete(testCaseId);
                Util.sendResponse(rc, 200, Map.of("deletedCount", deletedCount));
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "find test case handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void importTestCases(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            String stringId = rc.pathParam("projectId");
            try {
                Integer projectId = Integer.parseInt(stringId);
                Integer userId = Integer.parseInt(rc.user().principal().getString("sub"));
                List<FileUpload> fileUploads = rc.fileUploads();
                if (fileUploads.size() != 1) {
                    Util.sendResponse(rc, 500, "Number of file is incorrect");
                    return;
                }
                String contentType = fileUploads.get(0).contentType();
                if (!"text/csv".equals(contentType)) {
                    throw new IllegalArgumentException("Content type is invalid: " + contentType);
                }
                List<TestCase> testCases = new ArrayList<>();
                Map<String, Priorities> prioritiesMap = PrioritiesService.findAll().stream().collect(Collectors.toMap(x -> x.getPriorityName(), x -> x));
                Map<String, Sections> sectionsMap = SectionsService.findAllByProjectId(projectId).stream().collect(Collectors.toMap(x -> x.getSectionName(), x -> x));

                try (Scanner scanner = new Scanner(new File(fileUploads.get(0).uploadedFileName()));) {
                    // Skip headline
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }
                    while (scanner.hasNextLine()) {
                        String[] values = scanner.nextLine().split(COMMA_DELIMITER);
                        TestCase testCase = new TestCase();
                        testCase.setCaseName(values[0]);
                        if (values[1] != null && !values[1].equals("null"))
                            testCase.setEstimate(Integer.parseInt(values[1]));

                        Priorities priority = prioritiesMap.get(values[2]);
                        if (priority == null) {
                            throw new IllegalArgumentException("Priority not found: " + values[2]);
                        }
                        testCase.setPriorityId(priority.getPrioritiesId());

                        Sections section = sectionsMap.get(values[3]);
                        if (section == null) {
                            section = new Sections();
                            section.setProjectId(projectId);
                            section.setSectionName(values[3]);
                            SectionsService.create(section);
                            if (section.getSectionId() == null) {
                                throw new IllegalArgumentException("Create section failed: " + values[3]);
                            }
                            sectionsMap.put(values[3], section);
                        }
                        testCase.setSectionId(section.getSectionId());
                        testCase.setProjectId(projectId);
                        testCase.setUserId(userId);
                        testCase.setUpdatedBy(userId);
                        testCases.add(testCase);
                    }
                }
                TestCaseService.createBatch(testCases);
                Util.sendResponse(rc, 200, "successfully import test cases");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "import test case failed {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
