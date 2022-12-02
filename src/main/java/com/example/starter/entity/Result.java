package com.example.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"test_run_id", "test_case_id"})})
public class Result {

    public Result() {}

    public Result(Result result, String caseName, String sectionName) {
        this.resultId = result.resultId;
        this.runId = result.runId;
        this.caseId = result.caseId;
        this.status = result.status;
        this.comment = result.comment;
        this.caseName = caseName;
        this.sectionName = sectionName;
    }

    @Id
    @Column(name = "result_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @Column(name = "test_run_id", nullable = false)
    private Integer runId;

    @Column(name = "test_case_id", nullable = false)
    private Integer caseId;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Untested";

    @Column(name = "comment", length = 250)
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_run_id", nullable = false, insertable = false, updatable = false)
    private TestRun testRun;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", nullable = false, insertable = false, updatable = false)
    private TestCase testCase;

    public Integer getResultId() {
        return this.resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getRunId() {
        return runId;
    }

    public void setRunId(final Integer runId) {
        this.runId = runId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(final Integer caseId) {
        this.caseId = caseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(final TestRun testRun) {
        this.testRun = testRun;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(final TestCase testCase) {
        this.testCase = testCase;
    }

    @Transient
    private String caseName;

    public String getCaseName() {
        return this.caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    @Transient
    private String sectionName;

    public String getSectionName() {
        return this.sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
