package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.ResraurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.dto.RestaurantFoodDTO;
import com.cybersoft.food_project.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
    List<ResraurantDTO> getFeaturedRestaurant();
    RestaurantDetailDTO getDetailRestaurant(int id);
    public RestaurantFoodDTO getFoodDetailRestaurant(int id);
}
