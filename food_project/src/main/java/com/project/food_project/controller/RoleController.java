package com.project.food_project.controller;

import com.project.food_project.entity.UserEntity;
import com.project.food_project.entity.UserRoleEntity;
import com.project.food_project.repository.UserRepository;
import com.project.food_project.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/role")
@PreAuthorize("hasAuthority('MEMBER')")
public class RoleController {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRepository userRepository;
    @PostMapping("")
    public ResponseEntity<?> findall(){
        List<UserEntity> userEntities =userRepository.findAll();
        return ResponseEntity.ok().body(userEntities);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable int id){
        Optional<UserEntity> userEntities =userRepository.findById(id);

        return ResponseEntity.ok().body(userEntities);
    }
    @PostMapping("/creat")
    public ResponseEntity<?> creat(@RequestParam(name = "iduser") int iduser,@RequestParam(name = "idrole") int idrole){
        UserRoleEntity userRoleEntity=new UserRoleEntity();
        userRoleEntity.setIdRole(idrole);
        userRoleEntity.setIdUser(iduser);
        return ResponseEntity.ok().body(userRoleRepository.save(userRoleEntity));
    }
}
