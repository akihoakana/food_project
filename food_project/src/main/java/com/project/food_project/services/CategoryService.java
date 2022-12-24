package com.project.food_project.services;

import com.project.food_project.dto.CategoryDTO;
import com.project.food_project.dto.CategoryOutstandingDTO;
import com.project.food_project.dto.CategoryExplorerDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryExplorerDTO> getExplorerCategory();
    public CategoryOutstandingDTO getOutstandingCategory(int id);
    public CategoryDTO getCategoryById(int id);
}
