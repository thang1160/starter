package com.example.starter.dto;

public class TestCase {
    private String title;
    private Integer sectionId;
    private Integer templateId;
    private Integer typeId;
    private Integer priorityId;
    private Integer estimate;
    private String preconditions;
    private String steps;
    private String expectedResult;
    private Integer userId;
    private Integer statusId;
    private boolean deleted;
    private Integer updatedBy;
    private Integer projectId;

    public TestCase() {}

    public TestCase(String title, Integer sectionId, Integer templateId, Integer typeId, Integer priorityId, Integer estimate, String preconditions, String steps, String expectedResult, Integer userId, Integer projectId) {
        this.title = title;
        this.sectionId = sectionId;
        this.templateId = templateId;
        this.typeId = typeId;
        this.priorityId = priorityId;
        this.estimate = estimate;
        this.preconditions = preconditions;
        this.steps = steps;
        this.expectedResult = expectedResult;
        this.userId = userId;
        this.statusId = 1;
        this.deleted = false;
        this.updatedBy = userId;
        this.projectId = projectId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPriorityId() {
        return this.priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getEstimate() {
        return this.estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public String getPreconditions() {
        return this.preconditions;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getSteps() {
        return this.steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getExpectedResult() {
        return this.expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
