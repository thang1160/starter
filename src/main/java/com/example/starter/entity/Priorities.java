package com.example.starter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Priorities {

    @Id
    @Column(name = "priorities_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prioritiesId;

    @Column(name = "priority_Count", nullable = false)
    private Integer priorityCount;

    @Column(name = "priority_Name", nullable = false, length = 250)
    private String priorityName;

    @Column(name = "short_Name", nullable = false, length = 250)
    private String shortName;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    public Integer getPrioritiesId() {
        return prioritiesId;
    }

    public void setPrioritiesId(final Integer prioritiesId) {
        this.prioritiesId = prioritiesId;
    }

    public Integer getPriorityCount() {
        return priorityCount;
    }

    public void setPriorityCount(final Integer priorityCount) {
        this.priorityCount = priorityCount;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(final String priorityName) {
        this.priorityName = priorityName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(final Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(final Boolean isDelete) {
        this.isDelete = isDelete;
    }

}
