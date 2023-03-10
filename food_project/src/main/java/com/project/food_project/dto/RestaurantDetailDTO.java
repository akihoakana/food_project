package com.project.food_project.dto;

public class RestaurantDetailDTO {
    private String title = "";
    private String image = "";
    private float avgRate = 0;
    private String desc = "";
    private int countRate;

    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
