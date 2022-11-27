package com.example.starter.entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class TestPlan {

    @Id
    @Column(name = "plan_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;

    @Column(name = "plan_Name", nullable = false, length = 250)
    private String planName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "milestone_id", nullable = false)
    private Integer milestoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false, insertable = false, updatable = false)
    private Milestones milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
    private Projects project;

    @OneToMany(mappedBy = "plan")
    private Set<TestRun> planTestRuns;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(final Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(final String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(final Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Milestones getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestones milestone) {
        this.milestone = milestone;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(final Projects project) {
        this.project = project;
    }

    public Set<TestRun> getPlanTestRuns() {
        return planTestRuns;
    }

    public void setPlanTestRuns(final Set<TestRun> planTestRuns) {
        this.planTestRuns = planTestRuns;
    }

}
