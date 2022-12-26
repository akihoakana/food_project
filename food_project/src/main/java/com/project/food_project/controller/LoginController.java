package com.project.food_project.controller;

import com.project.food_project.jwt.JwtTokenHelper;
import com.project.food_project.payload.request.SignInRequest;
import com.project.food_project.payload.response.DataResponse;
import com.project.food_project.payload.response.DataTokenResponse;
import com.project.food_project.services.LoginService;
import com.project.food_project.services.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/signin")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    private long expiredDate = 8 * 60* 60 * 60 * 1000;
    private long refreshExpiredDate = 80 * 60 * 60 * 1000;

    @PostMapping("")
    public ResponseEntity<?> singin(@RequestBody SignInRequest request){

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        System.out.println("auth.getAuthorities() = " + auth.getAuthorities());
        String token = jwtTokenHelper.generateToken(request.getEmail(),"authen", (List) auth.getAuthorities(),expiredDate);
        String refeshToken = jwtTokenHelper.generateToken(request.getEmail(),"refesh", (List) auth.getAuthorities(),refreshExpiredDate);
        DataTokenResponse dataTokenResponse = new DataTokenResponse();
        dataTokenResponse.setToken(token);
        dataTokenResponse.setRefreshToken(refeshToken);
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDesc("");
        dataResponse.setData(dataTokenResponse);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PostMapping("/passwordnew")
    public ResponseEntity<?> NewPassord() {
        return new ResponseEntity<>("Update password thành công end",HttpStatus.OK);
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
