package com.project.food_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "roleEntity")
    @JsonIgnore
    private Set<UserRoleEntity> userRoleEntity;

    public Set<UserRoleEntity> getUserRoleEntity() {
        return userRoleEntity;
    }

    public void setUserRoleEntity(Set<UserRoleEntity> userRoleEntity) {
        this.userRoleEntity = userRoleEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
