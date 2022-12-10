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
public class Roles {

    @Id
    @Column(name = "role_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "role_Name", nullable = false, length = 250)
    private String roleName;

    @Column(name = "is_project_admin")
    private Boolean isProjectAdmin;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Users> roleUserss;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<RoleFunctionality> roleFunctionalities;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public Boolean getIsProjectAdmin() {
        return isProjectAdmin;
    }

    public void setIsProjectAdmin(final Boolean isProjectAdmin) {
        this.isProjectAdmin = isProjectAdmin;
    }

    public Set<Users> getRoleUserss() {
        return roleUserss;
    }

    public void setRoleUserss(final Set<Users> roleUserss) {
        this.roleUserss = roleUserss;
    }

    public Set<RoleFunctionality> getRoleFunctionalities() {
        return roleFunctionalities;
    }

    public void setRoleFunctionalities(final Set<RoleFunctionality> roleFunctionalities) {
        this.roleFunctionalities = roleFunctionalities;
    }

}

