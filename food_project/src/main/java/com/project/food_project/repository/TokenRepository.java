package com.project.food_project.repository;

import com.project.food_project.entity.TokenExpiredEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenExpiredEntity,Integer> {
    public TokenExpiredEntity findByName(String token);
}
