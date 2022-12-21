package com.cybersoft.food_project.model;

public class FoodModel {
    private int id;
    private String name;
    private String image;
    private double avgRate;
//    private String typeFood;
//
//    public String getTypeFood() {
//        return typeFood;
//    }
//
//    public void setTypeFood(String typeFood) {
//        this.typeFood = typeFood;
//    }
private int countRate;

    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }
    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
