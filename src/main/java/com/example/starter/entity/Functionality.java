package com.example.starter.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Functionality {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer functionalityId;

    @Column(length = 100)
    private String functionalityName;

    @JsonIgnore
    @OneToMany(mappedBy = "functionality")
    private Set<RoleFunctionality> roleFunctionalities;

    public Integer getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(final Integer functionalityId) {
        this.functionalityId = functionalityId;
    }

    public String getFunctionalityName() {
        return functionalityName;
    }

    public void setFunctionalityName(final String functionalityName) {
        this.functionalityName = functionalityName;
    }

    public Set<RoleFunctionality> getRoleFunctionalities() {
        return roleFunctionalities;
    }

    public void setRoleFunctionalities(
            final Set<RoleFunctionality> roleFunctionalities) {
        this.roleFunctionalities = roleFunctionalities;
    }

    @Override
    public String toString() {
        return "Functionality [functionalityId=" + functionalityId + ", functionalityName=" + functionalityName + "]";
    }

}
