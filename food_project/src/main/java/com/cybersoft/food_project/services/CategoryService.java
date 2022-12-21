package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.CategoryDTO;
import com.cybersoft.food_project.dto.CategoryOutstandingDTO;
import com.cybersoft.food_project.dto.CategoryExplorerDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryExplorerDTO> getExplorerCategory();
    public CategoryOutstandingDTO getOutstandingCategory(int id);
    public CategoryDTO getCategoryById(int id);
}
