package com.example.starter.dto;

import java.sql.Date;

public class TestRun{

    private String name;
    private String Description;
    private Date created_on;
    private Integer milestoneId;
    private Integer userId;
    private Integer projectid;
    private Integer planId;
    private boolean isCompleted;
    private Date completed_on;
    private Boolean includeAll;
    private Integer passed_count;
    private Integer retest_count;
    private Integer failed_count;
    private Integer untesed_count;
    private Integer assigned_to_id;
    
    public TestRun() {
    }
    public TestRun(String name, String Description, int milestoneId, int userId, int projectid, int planId, boolean isCompleted,  int assigned_to_id) {
        this.name = name;
        this.Description = Description;
        this.milestoneId = milestoneId;
        this.userId = userId;
        this.projectid = projectid;
        this.planId = planId;
        this.isCompleted = isCompleted;
        this.assigned_to_id = assigned_to_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Date getCreated_on() {
        return this.created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
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

    public Integer getProjectid() {
        return this.projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getPlanId() {
        return this.planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public boolean isIsCompleted() {
        return this.isCompleted;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Date getCompleted_on() {
        return this.completed_on;
    }

    public void setCompleted_on(Date completed_on) {
        this.completed_on = completed_on;
    }

    public Boolean isIncludeAll() {
        return this.includeAll;
    }

    public Boolean getIncludeAll() {
        return this.includeAll;
    }

    public void setIncludeAll(Boolean includeAll) {
        this.includeAll = includeAll;
    }

    public Integer getPassed_count() {
        return this.passed_count;
    }

    public void setPassed_count(Integer passed_count) {
        this.passed_count = passed_count;
    }

    public Integer getRetest_count() {
        return this.retest_count;
    }

    public void setRetest_count(Integer retest_count) {
        this.retest_count = retest_count;
    }

    public Integer getFailed_count() {
        return this.failed_count;
    }

    public void setFailed_count(Integer failed_count) {
        this.failed_count = failed_count;
    }

    public Integer getUntesed_count() {
        return this.untesed_count;
    }

    public void setUntesed_count(Integer untesed_count) {
        this.untesed_count = untesed_count;
    }

    public Integer getAssigned_to_id() {
        return this.assigned_to_id;
    }

    public void setAssigned_to_id(Integer assigned_to_id) {
        this.assigned_to_id = assigned_to_id;
    }

    
    

    


}