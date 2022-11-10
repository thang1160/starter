package com.example.starter.dto;

public class Project {
    private int id;
    private String name;
    private String announcement;
    private int activeTestRun;
    private int activeMilestone;

    public Project() {}

    public Project(int id, String name, int activeTestRun, int activeMilestone) {
        this.id = id;
        this.name = name;
        this.activeTestRun = activeTestRun;
        this.activeMilestone = activeMilestone;
    }

    public Project(String name, String announcement) {
        this.name = name;
        this.announcement = announcement;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnouncement() {
        return this.announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public int getActiveTestRun() {
        return this.activeTestRun;
    }

    public void setActiveTestRun(int activeTestRun) {
        this.activeTestRun = activeTestRun;
    }

    public int getActiveMilestone() {
        return this.activeMilestone;
    }

    public void setActiveMilestone(int activeMilestone) {
        this.activeMilestone = activeMilestone;
    }
}
