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
public class TestRun {

    public TestRun() {}

    public TestRun(TestRun testRun, String fullname) {
        this.runId = testRun.runId;
        this.runName = testRun.runName;
        this.createdOn = testRun.createdOn;
        this.passedCount = testRun.passedCount;
        this.retestCount = testRun.retestCount;
        this.failedCount = testRun.failedCount;
        this.untestedCount = testRun.untestedCount;
        this.fullname = fullname;
    }

    @Id
    @Column(name = "run_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer runId;

    @Column(name = "run_Name", nullable = false, length = 250)
    private String runName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn = LocalDate.now();

    @Column(name = "milestone_id")
    private Integer milestoneId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "completed_on")
    private LocalDate completedOn;

    @Column(name = "include_all", nullable = false)
    private Boolean includeAll = false;

    @Column(name = "passed_count")
    private Integer passedCount;

    @Column(name = "retest_count")
    private Integer retestCount;

    @Column(name = "failed_count")
    private Integer failedCount;

    @Column(name = "untested_count")
    private Integer untestedCount;

    @Column(name = "assigned_to_id")
    private Integer assignedToId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false, insertable = false, updatable = false)
    private Milestones milestone;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Users user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
    private Projects project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id", insertable = false, updatable = false)
    private Users assignedTo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", insertable = false, updatable = false)
    private TestPlan plan;

    @JsonIgnore
    @OneToMany(mappedBy = "testRun")
    private Set<Result> testRunResults;

    public Integer getRunId() {
        return runId;
    }

    public void setRunId(final Integer runId) {
        this.runId = runId;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(final String runName) {
        this.runName = runName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(final Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDate getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(final LocalDate completedOn) {
        this.completedOn = completedOn;
    }

    public Boolean getIncludeAll() {
        return includeAll;
    }

    public void setIncludeAll(final Boolean includeAll) {
        this.includeAll = includeAll;
    }

    public Integer getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(final Integer passedCount) {
        this.passedCount = passedCount;
    }

    public Integer getRetestCount() {
        return retestCount;
    }

    public void setRetestCount(final Integer retestCount) {
        this.retestCount = retestCount;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(final Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Integer getUntestedCount() {
        return untestedCount;
    }

    public void setUntestedCount(final Integer untestedCount) {
        this.untestedCount = untestedCount;
    }

    public Integer getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(final Integer assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Milestones getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestones milestone) {
        this.milestone = milestone;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(final Projects project) {
        this.project = project;
    }

    public Users getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final Users assignedTo) {
        this.assignedTo = assignedTo;
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

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getPlanId() {
        return this.planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public TestPlan getTestPlan() {
        return plan;
    }

    public void setTestPlan(final TestPlan testPlan) {
        this.plan = testPlan;
    }

    public Set<Result> getTestRunResults() {
        return testRunResults;
    }

    public void setTestRunResults(final Set<Result> testRunResults) {
        this.testRunResults = testRunResults;
    }

    @Transient
    private String fullname;

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "TestRun [runId=" + runId + ", runName=" + runName + ", description=" + description + ", createdOn=" + createdOn + ", milestoneId=" + milestoneId + ", userId=" + userId + ", projectId=" + projectId + ", planId=" + planId + ", isCompleted=" + isCompleted + ", completedOn=" + completedOn + ", includeAll=" + includeAll + ", passedCount=" + passedCount + ", retestCount=" + retestCount + ", failedCount=" + failedCount + ", untestedCount=" + untestedCount + ", assignedToId=" + assignedToId + "]";
    }
}
