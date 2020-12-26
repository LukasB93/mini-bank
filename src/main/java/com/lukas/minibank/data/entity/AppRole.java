package com.lukas.minibank.data.entity;

import javax.persistence.*;

@Entity
@Table(name="APP_ROLE")
public class AppRole  {
    @Id
    @Column(name="ROLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    @Column(name="ROLE_NAME")
    private String roleName;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
