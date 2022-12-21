package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.ResraurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.dto.RestaurantFoodDTO;
import com.cybersoft.food_project.entity.FoodEntity;
import com.cybersoft.food_project.entity.FoodReviewEntity;
import com.cybersoft.food_project.entity.RestaurantEntity;
import com.cybersoft.food_project.entity.RestaurantReviewEntity;
import com.cybersoft.food_project.model.FoodModel;
import com.cybersoft.food_project.repository.FoodRepository;
import com.cybersoft.food_project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantServiceImp implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public List<ResraurantDTO> getFeaturedRestaurant() {
        List<ResraurantDTO> dtos = new ArrayList<>();
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        for (RestaurantEntity data : restaurantEntities) {
            ResraurantDTO resraurantDTO = new ResraurantDTO();
            resraurantDTO.setTitle(data.getName());
            resraurantDTO.setImage(data.getImage());
            resraurantDTO.setMainFood(data.getMainfood());
            resraurantDTO.setCountRate(data.getRestaurantReviews().size());
            float avgRate = 0;
            float avgPrice = 0;
            float sumPrice = 0;
            float sumRate = 0;
            for (RestaurantReviewEntity dataReview: data.getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            }
            if(data.getRestaurantReviews().size() > 0){
                avgRate = sumRate/data.getRestaurantReviews().size();
            }
            for (FoodEntity foodEntity: data.getFoods()) {
                sumPrice += foodEntity.getPrice();
            }
            if(data.getFoods().size() > 0){
                avgPrice = sumPrice/data.getFoods().size();
            }
            resraurantDTO.setAvgRate(avgRate);
            resraurantDTO.setAvgPrice(avgPrice);
            dtos.add(resraurantDTO);
        }

        return dtos;
    }

    @Override
    public RestaurantDetailDTO getDetailRestaurant(int id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        if(restaurantEntity.isPresent()){
            restaurantDetailDTO.setTitle(restaurantEntity.get().getName());
            restaurantDetailDTO.setImage(restaurantEntity.get().getImage());
            restaurantDetailDTO.setCountRate(restaurantEntity.get().getRestaurantReviews().size());
            float avgRate = 0;
            float sumRate = 0;
            for (RestaurantReviewEntity dataReview: restaurantEntity.get().getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            }
            if(restaurantEntity.get().getRestaurantReviews().size() > 0){
                avgRate = sumRate/restaurantEntity.get().getRestaurantReviews().size();
            }
            restaurantDetailDTO.setAvgRate(avgRate);
        }

        return restaurantDetailDTO;
    }
    @Override
    public RestaurantFoodDTO getFoodDetailRestaurant(int id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        Set<FoodModel> foodModels = new HashSet<>();
        RestaurantFoodDTO restaurantFoodDTO = new RestaurantFoodDTO();
        if(restaurantEntity.isPresent()){
            restaurantFoodDTO.setName(restaurantEntity.get().getName());
            double avgRate = 0;
            double sumRate = 0;
            for (FoodEntity foodEntity: restaurantEntity.get().getFoods()) {
                FoodModel foodModel =new FoodModel();
                foodModel.setId(foodEntity.getId());
                foodModel.setImage(foodEntity.getImage());
                foodModel.setName(foodEntity.getName());
                foodModel.setCountRate(foodEntity.getFoodReviews().size());
                    for (FoodReviewEntity foodReviewEntity : foodEntity.getFoodReviews()) {
                        sumRate = sumRate + foodReviewEntity.getRate();
                    }
                    if (foodEntity.getFoodReviews().size() > 0) {
                        avgRate = sumRate / foodEntity.getFoodReviews().size();
                    }
                    foodModel.setAvgRate(avgRate);
                    foodModels.add(foodModel);
            }
            restaurantFoodDTO.setFood(foodModels);
        }
        return restaurantFoodDTO;
    }


}
