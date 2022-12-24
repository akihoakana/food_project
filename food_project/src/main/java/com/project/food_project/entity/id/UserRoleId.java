package com.project.food_project.entity.id;

import java.io.Serializable;

public class UserRoleId implements Serializable {
    private int idUser;
    private int idRole;

    public UserRoleId(int idUser, int idRole) {
        this.idUser = idUser;
        this.idRole = idRole;
    }
    public UserRoleId() {
    }
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
}
