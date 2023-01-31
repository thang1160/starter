package com.example.starter.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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
                        String line = scanner.nextLine();
                        if (StringUtils.isBlank(line)) {
                            break;
                        }
                        String[] values = line.split(COMMA_DELIMITER);
                        validateFileContent(values);
                        TestCase testCase = new TestCase();
                        testCase.setCaseName(values[0]);
                        if (!"".equals(values[1]) && !"null".equals(values[1]))
                            testCase.setEstimate(Integer.parseInt(values[1]));

                        String priorityName = values[2];
                        Priorities priority = prioritiesMap.get(priorityName);
                        if (priority == null) {
                            throw new IllegalArgumentException("Priority not found: " + priorityName);
                        }
                        testCase.setPriorityId(priority.getPrioritiesId());

                        String sectionName = values[3];
                        Sections section = sectionsMap.get(sectionName);
                        if (section == null) {
                            section = new Sections();
                            section.setProjectId(projectId);
                            section.setSectionName(sectionName);
                            SectionsService.create(section);
                            if (section.getSectionId() == null) {
                                throw new IllegalArgumentException("Create section failed: " + sectionName);
                            }
                            sectionsMap.put(sectionName, section);
                        }
                        testCase.setSectionId(section.getSectionId());
                        testCase.setProjectId(projectId);
                        testCase.setUserId(userId);
                        testCase.setUpdatedBy(userId);
                        testCases.add(testCase);
                    }
                }
                if (testCases.isEmpty()) {
                    Util.sendResponse(rc, 500, "File not contain test cases");
                    return;
                }
                TestCaseService.createBatch(testCases);
                Util.sendResponse(rc, 200, "successfully import test cases");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "import test case failed {0}:{1}", new Object[] {stringId, e});
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void validateFileContent(String[] values) {
        if (values.length < 4) {
            throw new IllegalArgumentException("Not enough parameters");
        }
        if (StringUtils.isBlank(values[0])) {
            throw new IllegalArgumentException("Test case name is empty");
        }
        values[0] = values[0].trim();
        if (!StringUtils.isNumeric(values[1]) && !"null".equals(values[1]) && !"".equals(values[1])) {
            throw new IllegalArgumentException("Estimate is invalid: " + values[1]);
        }
        if (StringUtils.isBlank(values[2])) {
            throw new IllegalArgumentException("Priority name is empty");
        }
        values[2] = values[2].trim();
        if (StringUtils.isBlank(values[3])) {
            throw new IllegalArgumentException("Section name is empty");
        }
        values[3] = values[3].trim();
    }
}
