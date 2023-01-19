package com.project.food_project.controller;

import com.project.food_project.dto.ResraurantDTO;
import com.project.food_project.dto.RestaurantDetailDTO;
import com.project.food_project.dto.RestaurantFoodDTO;
import com.project.food_project.services.RestaurantServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImp restaurantServiceImp;
    @GetMapping("/test")
    public String test(){
        return "Hello restaurant";
    }
    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedRestaurant(){
        List<ResraurantDTO> responseEntities = restaurantServiceImp.getFeaturedRestaurant();
        return new ResponseEntity<>(responseEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailRestaurant(@PathVariable("id") int id){
        RestaurantDetailDTO detailDTO = restaurantServiceImp.getDetailRestaurant(id);
        return new ResponseEntity<>(detailDTO, HttpStatus.OK);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getFoodDetailRestaurant(@PathVariable("id") int id){
        RestaurantFoodDTO restaurantFoodDTO = restaurantServiceImp.getFoodDetailRestaurant(id);
        return new ResponseEntity<>(restaurantFoodDTO, HttpStatus.OK);
    }

}
