package com.cybersoft.food_project.repository;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.payload.request.SignUpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    List<UserEntity> findByEmailAndPassword(String email,String password);
    List<UserEntity> findByEmail(String email);
    UserEntity findByVerifyCode(String verifyCode);
}
