package com.example.starter.entity;

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
public class Projects {

    public Projects() {}

    public Projects(Integer projectId, String projectName, Long activeMilestone, Long activeTestRun) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.activeMilestone = activeMilestone;
        this.activeTestRun = activeTestRun;
    }

    @Id
    @Column(name = "project_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(name = "project_Name", nullable = false, length = 250)
    private String projectName;

    @Column(name = "announcement", columnDefinition = "text")
    private String announcement;

    @Column(name = "master_id")
    private Integer masterId;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Milestones> projectMilestoness;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", insertable = false, updatable = false)
    private Users master;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<TestRun> projectTestRuns;

    @Transient
    private Long activeMilestone;

    @Transient
    private Long activeTestRun;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(final Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(final String announcement) {
        this.announcement = announcement;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(final Integer masterId) {
        this.masterId = masterId;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Set<Milestones> getProjectMilestoness() {
        return projectMilestoness;
    }

    public void setProjectMilestoness(final Set<Milestones> projectMilestoness) {
        this.projectMilestoness = projectMilestoness;
    }

    public Users getMaster() {
        return master;
    }

    public void setMaster(final Users master) {
        this.master = master;
    }

    public Set<TestRun> getProjectTestRuns() {
        return projectTestRuns;
    }

    public void setProjectTestRuns(final Set<TestRun> projectTestRuns) {
        this.projectTestRuns = projectTestRuns;
    }

    public Long getActiveMilestone() {
        return this.activeMilestone;
    }

    public void setActiveMilestone(Long activeMilestone) {
        this.activeMilestone = activeMilestone;
    }

    public Long getActiveTestRun() {
        return this.activeTestRun;
    }

    public void setActiveTestRun(Long activeTestRun) {
        this.activeTestRun = activeTestRun;
    }

    @Override
    public String toString() {
        return "Projects [projectId=" + projectId + ", projectName=" + projectName + ", announcement=" + announcement + ", masterId=" + masterId + ", completed=" + completed + "]";
    }
}
