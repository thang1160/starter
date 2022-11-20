package com.example.starter.model;

import java.time.LocalDate;

public class MilestoneDTO {
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int projectId;
    private boolean completed;
    private boolean started;
    private LocalDate completeOn;

    public MilestoneDTO(String name, String description, LocalDate startDate, LocalDate endDate, int projectId, boolean completed) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.completed = completed;
        this.started = false;
        if (completed) {
            completeOn = LocalDate.now();
        } else {
            completeOn = endDate;
        }
    }

    public MilestoneDTO() {}

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public boolean isStarted() {
        return this.started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public LocalDate getCompleteOn() {
        return this.completeOn;
    }

    public void setCompleteOn(LocalDate completeOn) {
        this.completeOn = completeOn;
    }
}
