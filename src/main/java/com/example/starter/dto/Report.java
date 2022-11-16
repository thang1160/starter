package com.example.starter.dto;

import java.sql.Date;

public class Report {
     private String name;
     private String description;
     private int projectId;
     private int created_by;
     private Date created_on;
     private int excuted_on;
     private int milestoneId;
    private int planId;

    public Report(String name, String description, int projectId, int created_by, int milestoneId, int planId) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.created_by = created_by;
        
        this.milestoneId = milestoneId;
        this.planId = planId;
    }

    public Report(String title, Integer estimate, Integer sectionId, Integer priorityId, Integer milestoneId2, String preconditions, String steps, Integer userId, String expectedResult, Integer projectId2) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_on() {
        return this.created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public int getExcuted_on() {
        return this.excuted_on;
    }

    public void setExcuted_on(int excuted_on) {
        this.excuted_on = excuted_on;
    }

    public int getMilestoneId() {
        return this.milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getPlanId() {
        return this.planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

}
