package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.payload.request.SignUpRequest;
import com.cybersoft.food_project.services.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    private LoginServiceImp loginServiceImp;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping("")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request, Errors errors) throws UnsupportedEncodingException, MessagingException {
        return new ResponseEntity<>(loginServiceImp.registerNewUserAccount(signUpRequest,getSiteURL(request)),HttpStatus.OK);
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendconfirmByEmail(@RequestParam String code, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        loginServiceImp.resendconfirmByEmail(code,getSiteURL(request));
        return new ResponseEntity<>(HttpStatus.OK,HttpStatus.OK);
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<?> confirmByEmail(@PathVariable("code") String code) {
        return new ResponseEntity<>(loginServiceImp.confirmByEmail(code),HttpStatus.OK);
    }

    @GetMapping("/forgetpassword")
    public ResponseEntity<?> forgetPassord(@RequestParam String mail, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        return new ResponseEntity<>(loginServiceImp.forgetPassord(mail,getSiteURL(request)),HttpStatus.OK);
    }
    @PostMapping("/newpassword")
    public ResponseEntity<?> newPassord(@RequestParam String password,@RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (loginServiceImp.newPassord(code,password)){
            response.sendRedirect(
                    "http://localhost:8081/api/signup/passwordnew");
        }
        return new ResponseEntity<>("Update password thành côngg",HttpStatus.OK);
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
