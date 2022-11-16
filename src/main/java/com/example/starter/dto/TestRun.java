package com.example.starter.dto;

public class TestRun {
    private String name;
    private int milestoneId;
    private int assignedToId;
    private String description;
    private int testType;
    private int userId;
    private int projectId;
    private boolean completed;
    private boolean includeAll;

    public TestRun(String name, int milestoneId, int assignedToId, String description, int testType, int userId, int projectId) {
        this.name = name;
        this.milestoneId = milestoneId;
        this.assignedToId = assignedToId;
        this.description = description;
        this.testType = testType;
        this.userId = userId;
        this.projectId = projectId;
        this.completed = false;
        includeAll = testType == 1;
    }

    public TestRun() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMilestoneId() {
        return this.milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getAssignedToId() {
        return this.assignedToId;
    }

    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTestType() {
        return this.testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isIncludeAll() {
        return this.includeAll;
    }

    public void setIncludeAll(boolean includeAll) {
        this.includeAll = includeAll;
    }

}
