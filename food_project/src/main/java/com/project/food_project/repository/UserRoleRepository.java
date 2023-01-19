package com.project.food_project.repository;


import com.project.food_project.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Integer> {
//    List<UserRoleEntity> findByIdUser(int id);
}
