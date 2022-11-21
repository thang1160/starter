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
public class Sections {

    @Id
    @Column(name = "section_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sectionId;

    @Column(name = "section_Name", nullable = false, length = 50)
    private String sectionName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "plan_id", nullable = false)
    private Integer planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
    private Projects project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false, insertable = false, updatable = false)
    private TestPlan plan;

    @OneToMany(mappedBy = "section")
    private Set<TestCase> sectionTestCases;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(final Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(final String sectionName) {
        this.sectionName = sectionName;
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

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(final Integer planId) {
        this.planId = planId;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(final Projects project) {
        this.project = project;
    }

    public TestPlan getTestPlan() {
        return plan;
    }

    public void setTestPlan(final TestPlan testPlan) {
        this.plan = testPlan;
    }

    public Set<TestCase> getSectionTestCases() {
        return sectionTestCases;
    }

    public void setSectionTestCases(final Set<TestCase> sectionTestCases) {
        this.sectionTestCases = sectionTestCases;
    }

}
