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
public class TestCase {

    public TestCase() {}

    public TestCase(TestCase testCase, String fullname) {
        this.caseId = testCase.caseId;
        this.caseName = testCase.caseName;
        this.estimate = testCase.estimate;
        this.sectionId = testCase.sectionId;
        this.priorityId = testCase.priorityId;
        this.milestoneId = testCase.milestoneId;
        this.userId = testCase.userId;
        this.preconditions = testCase.preconditions;
        this.steps = testCase.steps;
        this.expectedResult = testCase.expectedResult;
        this.statusId = testCase.statusId;
        this.isDeleted = testCase.isDeleted;
        this.createdOn = testCase.createdOn;
        this.updatedOn = testCase.updatedOn;
        this.updatedBy = testCase.updatedBy;
        this.projectId = testCase.projectId;
        this.user = new Users();
        this.user.setFullname(fullname);
    }

    public TestCase(TestCase testCase, Users user, Sections section) {
        this.caseId = testCase.caseId;
        this.caseName = testCase.caseName;
        this.estimate = testCase.estimate;
        this.sectionId = testCase.sectionId;
        this.priorityId = testCase.priorityId;
        this.milestoneId = testCase.milestoneId;
        this.userId = testCase.userId;
        this.preconditions = testCase.preconditions;
        this.steps = testCase.steps;
        this.expectedResult = testCase.expectedResult;
        this.statusId = testCase.statusId;
        this.isDeleted = testCase.isDeleted;
        this.createdOn = testCase.createdOn;
        this.updatedOn = testCase.updatedOn;
        this.updatedBy = testCase.updatedBy;
        this.projectId = testCase.projectId;
        this.sectionName = section.getSectionName();
        this.fullname = user.getFullname();
    }

    @Id
    @Column(name = "case_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId;

    @Column(name = "case_Name", nullable = false, length = 250)
    private String caseName;

    @Column(name = "estimate")
    private Integer estimate;

    @Column(name = "section_id", nullable = false)
    private Integer sectionId;

    @Column(name = "priority_id", nullable = false)
    private Integer priorityId;

    @Column(name = "milestone_id")
    private Integer milestoneId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "preconditions", columnDefinition = "text")
    private String preconditions;

    @Column(name = "steps", columnDefinition = "text")
    private String steps;

    @Column(name = "expected_result", columnDefinition = "text")
    private String expectedResult;

    @Column(name = "status_id", nullable = false)
    private Integer statusId = 1;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn = LocalDate.now();

    @Column(name = "updated_on", nullable = false)
    private LocalDate updatedOn = LocalDate.now();

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", insertable = false, updatable = false)
    private Milestones milestone;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", nullable = false, insertable = false, updatable = false)
    private Priorities priority;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
    private Projects project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", insertable = false, updatable = false)
    private Sections section;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false, insertable = false, updatable = false)
    private Status status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "testCase")
    private Set<Result> testCaseResults;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(final Integer caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(final String caseName) {
        this.caseName = caseName;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(final Integer estimate) {
        this.estimate = estimate;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(final Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(final Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(final Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(final String preconditions) {
        this.preconditions = preconditions;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(final String steps) {
        this.steps = steps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(final String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(final Integer statusId) {
        this.statusId = statusId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(final LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
    }

    public Milestones getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestones milestone) {
        this.milestone = milestone;
    }

    public Priorities getPriority() {
        return priority;
    }

    public void setPriority(final Priorities priority) {
        this.priority = priority;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(final Projects project) {
        this.project = project;
    }

    public Sections getSection() {
        return section;
    }

    public void setSection(final Sections section) {
        this.section = section;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    public Set<Result> getTestCaseResults() {
        return testCaseResults;
    }

    public void setTestCaseResults(final Set<Result> testCaseResults) {
        this.testCaseResults = testCaseResults;
    }

    @Transient
    private String sectionName;
    @Transient
    private String fullname;

    public String getSectionName() {
        return this.sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
