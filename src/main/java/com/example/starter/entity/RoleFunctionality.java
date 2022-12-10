package com.example.starter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class RoleFunctionality {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleFunctionalityId;

    @Column(nullable = false)
    private Integer functionalityId;

    @Column(nullable = false)
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionalityId", nullable = false, insertable = false, updatable = false)
    private Functionality functionality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", nullable = false, insertable = false, updatable = false)
    private Roles role;

    public Integer getRoleFunctionalityId() {
        return roleFunctionalityId;
    }

    public void setRoleFunctionalityId(final Integer roleFunctionalityId) {
        this.roleFunctionalityId = roleFunctionalityId;
    }

    public Integer getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(final Integer functionalityId) {
        this.functionalityId = functionalityId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public void setFunctionality(final Functionality functionality) {
        this.functionality = functionality;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(final Roles role) {
        this.role = role;
    }

}
