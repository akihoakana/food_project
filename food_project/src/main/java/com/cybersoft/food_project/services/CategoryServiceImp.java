package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.CategoryDTO;
import com.cybersoft.food_project.dto.CategoryOutstandingDTO;
import com.cybersoft.food_project.dto.CategoryExplorerDTO;
import com.cybersoft.food_project.entity.CategoryEntity;
import com.cybersoft.food_project.entity.FoodEntity;
import com.cybersoft.food_project.entity.FoodReviewEntity;
import com.cybersoft.food_project.model.FoodModel;
import com.cybersoft.food_project.model.FoodOutstandingModel;
import com.cybersoft.food_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryExplorerDTO> getExplorerCategory() {
        List<CategoryExplorerDTO> categoryExplorerDTOS = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        for (CategoryEntity data:categoryEntities) {
            CategoryExplorerDTO categoryExplorerDTO =new CategoryExplorerDTO();
            categoryExplorerDTO.setName(data.getName());
            categoryExplorerDTO.setImage(data.getImage());
            categoryExplorerDTO.setCountOption(data.getFoods().size());
            categoryExplorerDTOS.add(categoryExplorerDTO);
        }
        return categoryExplorerDTOS;
    }
    @Override
    public CategoryOutstandingDTO getOutstandingCategory(int id) {
        CategoryOutstandingDTO categoryOutstandingDTO = new CategoryOutstandingDTO();
        Set<FoodOutstandingModel> foodOutstandingModels = new HashSet<>();
        Optional<CategoryEntity> categoryEntity=categoryRepository.findById(id);
        if (categoryEntity.isPresent()){
            categoryOutstandingDTO.setName(categoryEntity.get().getName());
            for (FoodEntity foodEntity :categoryEntity.get().getFoods()){
                FoodOutstandingModel foodOutstandingModel = new FoodOutstandingModel();
                foodOutstandingModel.setName(foodEntity.getName());
                foodOutstandingModel.setId(foodEntity.getId());
                foodOutstandingModel.setImage(foodEntity.getImage());
                foodOutstandingModels.add(foodOutstandingModel);
            }
            categoryOutstandingDTO.setFood(foodOutstandingModels);
        }
        return categoryOutstandingDTO;
    }@Override
    public CategoryDTO getCategoryById(int id) {
        CategoryDTO categoryDTO = new CategoryDTO();
        Set<FoodModel> foodModels = new HashSet<>();
        Optional<CategoryEntity> categoryEntity=categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            categoryDTO.setName(categoryEntity.get().getName());
//            categoryDTO.setImage(categoryEntity.get().getImage());
            double avgRate = 0;
            double sumRate = 0;
            for (FoodEntity foodEntity : categoryEntity.get().getFoods()) {
                FoodModel foodModel = new FoodModel();
                foodModel.setName(foodEntity.getName());
                foodModel.setImage(foodEntity.getImage());
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
            categoryDTO.setFoodModelSet(foodModels);
        }
        return categoryDTO;

    }
}
