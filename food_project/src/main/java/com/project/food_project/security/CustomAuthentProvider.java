package com.project.food_project.security;

import com.project.food_project.entity.RoleEntity;
import com.project.food_project.entity.UserEntity;
import com.project.food_project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserEntity userEntity = loginService.checkLogin(email);

        if(userEntity != null){
            boolean isMatchPassword = passwordEncoder.matches(password,userEntity.getPassword());
            if(isMatchPassword){
                List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
                for (RoleEntity roleEntity:loginService.getRoles(email)){
                    authList.add(new SimpleGrantedAuthority(roleEntity.getName()));
                }
                return new UsernamePasswordAuthenticationToken(userEntity.getEmail(),userEntity.getPassword(),authList);
            }else{
                return null;
            }

        }else{
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
