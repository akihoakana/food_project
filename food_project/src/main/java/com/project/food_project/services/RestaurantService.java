package com.project.food_project.services;

import com.project.food_project.dto.ResraurantDTO;
import com.project.food_project.dto.RestaurantDetailDTO;
import com.project.food_project.dto.RestaurantFoodDTO;

import java.util.List;

public interface RestaurantService {
    List<ResraurantDTO> getFeaturedRestaurant();
    RestaurantDetailDTO getDetailRestaurant(int id);
    public RestaurantFoodDTO getFoodDetailRestaurant(int id);
}
