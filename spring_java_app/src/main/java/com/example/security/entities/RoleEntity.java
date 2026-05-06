package com.example.security.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.example.security.config.RoleEnum;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permissions", 
               joinColumns = @JoinColumn(name = "role_id"), 
               inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissionList = new HashSet<>();

    public Long getId() {
        return id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Set<PermissionEntity> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<PermissionEntity> permissionList) {
        this.permissionList = permissionList;
    }

    public RoleEntity() {
    }

    public RoleEntity(Long id, RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
        this.id = id;
        this.roleEnum = roleEnum;
        this.permissionList = permissionList;
    }

    public RoleEntity(RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
        this.roleEnum = roleEnum;
        this.permissionList = permissionList;
    }
}
