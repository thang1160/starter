package com.example.starter.entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Status {

    @Id
    @Column(name = "status_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(name = "status_Name", nullable = false, length = 250)
    private String statusName;

    @OneToMany(mappedBy = "status")
    private Set<TestCase> statusTestCases;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(final Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(final String statusName) {
        this.statusName = statusName;
    }

    public Set<TestCase> getStatusTestCases() {
        return statusTestCases;
    }

    public void setStatusTestCases(final Set<TestCase> statusTestCases) {
        this.statusTestCases = statusTestCases;
    }

}
