package com.example.starter.entity;

import java.time.OffsetDateTime;
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
import jakarta.persistence.Transient;


@Entity
public class Report {

    public Report(Report report, Users user, Projects projects) {
        this.projectName = projects.getProjectName();
        this.fullName = user.getFullname();
        this.reportId = report.reportId;
        this.reportName = report.reportName;
        this.reportDescription = report.reportDescription;
        this.createdBy = report.createdBy;
        this.createdOn = report.createdOn;
        this.projectId = report.projectId;
        this.jsonData = report.jsonData;
    }

    public Report() {}

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @Column(nullable = false, length = 250)
    private String reportName;

    @Column(length = 250)
    private String reportDescription;

    @Column(nullable = false)
    private Integer createdBy;

    @Column(nullable = false)
    private OffsetDateTime createdOn = OffsetDateTime.now();

    @Column(nullable = false)
    private Integer projectId;

    @Column(nullable = false, columnDefinition = "text")
    private String jsonData = "{}";

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", nullable = false, insertable = false, updatable = false)
    private Users user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", nullable = false, insertable = false, updatable = false)
    private Projects project;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(final Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(final String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(final String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Integer createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
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

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(final String jsonData) {
        this.jsonData = jsonData;
    }

    @Transient
    private Set<Integer> testRunIds;

    @Transient
    private String projectName;

    @Transient
    private String fullName;

    public Set<Integer> getTestRunIds() {
        return this.testRunIds;
    }

    public void setTestRunIds(Set<Integer> testRunIds) {
        this.testRunIds = testRunIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
