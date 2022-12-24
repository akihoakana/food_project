package com.project.food_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.food_project.entity.id.UserRoleId;

import javax.persistence.*;

@Entity(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRoleEntity {
    @Id()
    @Column(name = "id_user")
    private int idUser;

    @Id
    @Column(name = "id_role")
    private int idRole;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private UserEntity userEntity;

    @ManyToOne()
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private RoleEntity roleEntity;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }
}
