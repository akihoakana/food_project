package com.project.food_project.services;

import com.project.food_project.entity.RoleEntity;
import com.project.food_project.entity.TokenExpiredEntity;
import com.project.food_project.entity.UserEntity;
import com.project.food_project.payload.request.LogInRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public  interface LoginService  {
    List<TokenExpiredEntity> invalidToken(String token);
    boolean checkToken(String token);
    UserEntity checkLogin(String email);
    List<RoleEntity> getRoles(String email);
    UserEntity registerNewUserAccount(LogInRequest logInRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    UserEntity confirmByEmail(String email);
    boolean emailExists(String email);
    void sendVerificationEmail(String email, String siteURL) throws UnsupportedEncodingException, MessagingException;
    List<UserEntity> newPassword(String email, String password, String passwordConfirm);
}
