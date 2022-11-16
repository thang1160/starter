package com.example.starter.dto;

import java.sql.Date;

public class Milestone{
    private String name;
    private String Description;
    private Integer project_id;
    private Date starDate;
    private Date endDate;
    private boolean isStarted;
    private Integer projectId;
    private boolean isCompleted;


    public Milestone() {
    }

    public Milestone(String name, String description, Integer project_id, boolean isStarted, boolean isCompleted) {
        this.name = name;
        Description = description;
        this.project_id = project_id;
        
        this.isStarted = isStarted;
        
        this.isCompleted = isCompleted;
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

    public Integer getProject_id() {
        return this.project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Date getStarDate() {
        return this.starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsStarted() {
        return this.isStarted;
    }

    public boolean getIsStarted() {
        return this.isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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


    

}