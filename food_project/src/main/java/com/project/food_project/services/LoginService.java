package com.project.food_project.services;

import com.project.food_project.entity.RoleEntity;
import com.project.food_project.entity.UserEntity;
import com.project.food_project.payload.request.SignUpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LoginService {
    boolean checkLogin(String email, String password);
    UserEntity checkLogin(String email);
    List<RoleEntity> getRoles(String email);
    UserEntity registerNewUserAccount(SignUpRequest signUpRequest, String siteURL) throws UnsupportedEncodingException, MessagingException;
    boolean emailExists(String email);
    void sendVerificationEmail(UserEntity userEntity, String siteURL) throws UnsupportedEncodingException, MessagingException;
    String confirmByEmail(String verifyCode);
    boolean newPassord(String verifyCode,String password);
    String forgetPassord(String mail, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void resendconfirmByEmail(String verifyCode, String siteURL) throws UnsupportedEncodingException, MessagingException;
}
