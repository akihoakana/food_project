package com.cybersoft.food_project.repository;

import com.cybersoft.food_project.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity,Integer> {
}
