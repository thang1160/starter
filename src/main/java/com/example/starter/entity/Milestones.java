package com.example.starter.entity;

import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Milestones {

    @Id
    @Column(nullable = false, updatable = false, name = "milestone_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer milestoneId;

    @Column(name = "milestone_Name", length = 250)
    private String milestoneName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_started", nullable = false)
    private Boolean isStarted = false;

    @Column(name = "due_on")
    private LocalDate dueOn;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "completed_on", nullable = false)
    private LocalDate completedOn;

    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable=false)
    private Projects project;

    @JsonIgnore
    @OneToMany(mappedBy = "milestone")
    private Set<TestRun> milestoneTestRuns;

    @Transient
    private Boolean completed;

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(final Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(final String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(final Boolean isStarted) {
        this.isStarted = isStarted;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public void setDueOn(final LocalDate dueOn) {
        this.dueOn = dueOn;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(final LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(final Boolean status) {
        this.status = status;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(final Projects project) {
        this.project = project;
    }

    public Set<TestRun> getMilestoneTestRuns() {
        return milestoneTestRuns;
    }

    public void setMilestoneTestRuns(final Set<TestRun> milestoneTestRuns) {
        this.milestoneTestRuns = milestoneTestRuns;
    }

    public Boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Milestones [milestoneId=" + milestoneId + ", milestoneName=" + milestoneName + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", isStarted=" + isStarted + ", dueOn=" + dueOn + ", projectId=" + projectId + ", completedOn=" + completedOn + ", status=" + status + "]";
    }
}
