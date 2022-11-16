package com.example.starter.dto;

import java.sql.Date;

public class TestCase {
    private String title;
    private Integer estimate;
    private Integer sectionId;
    private Integer milestoneId;
    private Integer userId;
    private String preconditions;
    private String steps;
    private Integer priorityId;
    private String expectedResult;
    private Integer statusId;
    private boolean deleted;
    private Date createOn;
    private Date updateOn;
    private Integer updatedBy;
    private Integer projectId;

    public TestCase(String title, Integer estimate, Integer sectionId, Integer milestoneId, Integer userId, String preconditions, String steps, Integer priorityId, String expectedResult, Integer projectId) {
        this.title = title;
        this.estimate = estimate;
        this.sectionId = sectionId;
        this.milestoneId = milestoneId;
        this.userId = userId;
        this.preconditions = preconditions;
        this.steps = steps;
        this.priorityId = priorityId;
        this.expectedResult = expectedResult;
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

    public Integer getEstimate() {
        return this.estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getMilestoneId() {
        return this.milestoneId;
    }

    public void setMilestoneId(Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getPriorityId() {
        return this.priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public String getExpectedResult() {
        return this.expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
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

    public Date getCreateOn() {
        return this.createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getUpdateOn() {
        return this.updateOn;
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

    public TestCase() {}

}
