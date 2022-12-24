package com.project.food_project.repository;

import com.project.food_project.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity,Integer> {
}
