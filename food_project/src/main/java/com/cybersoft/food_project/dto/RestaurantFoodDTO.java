package com.cybersoft.food_project.dto;

import com.cybersoft.food_project.model.FoodModel;

import java.util.Set;

public class RestaurantFoodDTO {
    //    private int id;
    private String name;
    private Set<FoodModel> food;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FoodModel> getFood() {
        return food;
    }

    public void setFood(Set<FoodModel> food) {
        this.food = food;
    }
}
