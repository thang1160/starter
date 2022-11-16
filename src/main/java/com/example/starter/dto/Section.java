package com.example.starter.dto;

public class Section {
    private String name;
    private String description;
    private int projectId;
    private int planId;


    public Section() {
    }

    public Section(String name, String description) {
        this.name = name;
        this.description = description;
        
        
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

    public int getPlanId() {
        return this.planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

}
