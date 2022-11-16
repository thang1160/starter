package com.example.starter.dto;

public class TestPlan {
    private String plan_name;   
    private String description;
    private Integer project_id;
    private Integer milestone_id;

    public TestPlan() {
    }

    public TestPlan(String plan_name, String description, Integer project_id, Integer milestone_id) {
        this.plan_name = plan_name;
        this.description = description;
        this.project_id = project_id;
        this.milestone_id = milestone_id;
    }

    public String getPlan_name() {
        return this.plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProject_id() {
        return this.project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getMilestone_id() {
        return this.milestone_id;
    }

    public void setMilestone_id(Integer milestone_id) {
        this.milestone_id = milestone_id;
    }
    
}