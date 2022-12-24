package com.project.food_project.dto;

import com.project.food_project.model.FoodModel;

import java.util.Set;

public class CategoryDTO {
//    private int id;
    private String name;
//    private String image;
    private Set<FoodModel> foodModelSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public Set<FoodModel> getFoodModelSet() {
        return foodModelSet;
    }

    public void setFoodModelSet(Set<FoodModel> foodModelSet) {
        this.foodModelSet = foodModelSet;
    }
}
