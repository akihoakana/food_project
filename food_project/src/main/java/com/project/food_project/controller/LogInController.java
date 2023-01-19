package com.project.food_project.controller;

import com.project.food_project.jwt.JwtTokenHelper;
import com.project.food_project.payload.request.NewPasswordRequest;
import com.project.food_project.payload.request.LogInRequest;
import com.project.food_project.payload.response.DataResponse;
import com.project.food_project.payload.response.DataTokenResponse;
import com.project.food_project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin
@RequestMapping("/signin")
public class LogInController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin")
    public String admin(){
        return SecurityContextHolder.getContext().toString();
    }


    @PostMapping("/regular")
    @PreAuthorize("hasAnyAuthority('regular')")
    public String regular(){
        return SecurityContextHolder.getContext().toString();
    }

    private long expiredDate = 8 * 60* 60 * 60 * 1000;
    private long refreshExpiredDate = 80 * 60 * 60 * 1000;

    @PostMapping("")
    public ResponseEntity<?> singin(@Valid @RequestBody LogInRequest request){

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        System.out.println("auth.getAuthorities() = " + auth.getAuthorities());
        String token = jwtTokenHelper.generateToken(request.getEmail(),"authen", auth.getAuthorities(),expiredDate);
        String refeshToken = jwtTokenHelper.generateToken(request.getEmail(),"refesh",  auth.getAuthorities(),refreshExpiredDate);
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
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.hasText(request.getHeader("Authorization")) && request.getHeader("Authorization").startsWith("Bearer ")){
            String token =request.getHeader("Authorization").substring(7);

            System.out.println("token = " + token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return ResponseEntity.ok(loginService.invalidToken(token));
        }
        else
        return ResponseEntity.ok("không có token");
    }
    @PostMapping("/newpassword/{email}")
    public ResponseEntity<?> newPassword(@PathVariable("email") String email, @Valid @RequestBody NewPasswordRequest newPasswordRequest) {
        return ResponseEntity.ok(loginService.newPassword(email, newPasswordRequest.getPassword(), newPasswordRequest.getPasswordConfirm()));
    }
    @PostMapping("/forgetpassword/{email}")
    public ResponseEntity<?> forgetPasword(@RequestParam(name = "email") String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String verifyURL = request.getRequestURL().toString()
                .replace(request.getServletPath(), "") + "/signin/newpassword/"+email;
         loginService.sendVerificationEmail(email,verifyURL);
        return ResponseEntity.ok("Đã gửi mail");
    }

}
