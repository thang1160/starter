package com.example.starter.entity;

import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Users {

    @Id
    @Column(name = "user_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "fullname", nullable = false, length = 250)
    private String fullname;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @JsonIgnore
    @Column(name = "salt", nullable = false, length = 250)
    private String salt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    private Roles role;

    @JsonIgnore
    @OneToMany(mappedBy = "master")
    private Set<Projects> masterProjectss;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<TestRun> userTestRuns;

    @JsonIgnore
    @OneToMany(mappedBy = "assignedTo")
    private Set<TestRun> assignedToTestRuns;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(final Roles role) {
        this.role = role;
    }

    public Set<Projects> getMasterProjectss() {
        return masterProjectss;
    }

    public void setMasterProjectss(final Set<Projects> masterProjectss) {
        this.masterProjectss = masterProjectss;
    }

    public Set<TestRun> getUserTestRuns() {
        return userTestRuns;
    }

    public void setUserTestRuns(final Set<TestRun> userTestRuns) {
        this.userTestRuns = userTestRuns;
    }

    public Set<TestRun> getAssignedToTestRuns() {
        return assignedToTestRuns;
    }

    public void setAssignedToTestRuns(final Set<TestRun> assignedToTestRuns) {
        this.assignedToTestRuns = assignedToTestRuns;
    }

    @Transient
    private List<Functionality> functionalities;

    public List<Functionality> getFunctionalities() {
        return this.functionalities;
    }

    public void setFunctionalities(List<Functionality> functionalities) {
        this.functionalities = functionalities;
    }

}
