package com.cybersoft.food_project.services;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.payload.request.SignUpRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface LoginService {
    boolean checkLogin(String email, String password);
    UserEntity checkLogin(String email);
    UserEntity registerNewUserAccount(SignUpRequest signUpRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    boolean emailExists(String email);
    void sendVerificationEmail(UserEntity userEntity, String siteURL) throws UnsupportedEncodingException, MessagingException;
    String confirmByEmail(String verifyCode);
    boolean newPassord(String verifyCode,String password);
    String forgetPassord(String mail, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void resendconfirmByEmail(String verifyCode, String siteURL) throws UnsupportedEncodingException, MessagingException;
}
