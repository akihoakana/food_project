package com.project.food_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;
//
//    @Column(name = "token")
//    private String token;
//
//    @Column(name = "type_token")
//    private String typeToken;
//
//    @Column(name = "phone_number")
//    private String phoneNumber;
//
//    @Column(name = "verify_code")
//    private String verifyCode;
//
//    @Column(name = "verify_code_expired")
//    private Date verifyCodeExpired;

    @Column(name = "email_verify")
    private boolean emailVerify;

    @OneToOne(mappedBy = "user")
    private UserDetailEntity userDetail;

    @OneToMany(mappedBy = "userEntity")
    private Set<UserRoleEntity> userRoleEntity;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<FoodReviewEntity> foodReviews;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<TOtherEntity> tOthers;
}
