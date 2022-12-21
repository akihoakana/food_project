package com.cybersoft.food_project.dto;

import com.cybersoft.food_project.model.FoodOutstandingModel;

import java.util.Set;

public class CategoryOutstandingDTO {
//    private int id;
    private String name;
    private Set<FoodOutstandingModel> food;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FoodOutstandingModel> getFood() {
        return food;
    }

    public void setFood(Set<FoodOutstandingModel> food) {
        this.food = food;
    }
}
