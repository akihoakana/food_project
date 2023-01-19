package com.project.food_project.model;

import com.project.food_project.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseModel {
    private UserEntity usersEntity;
    private String linkActive;
}
