package com.project.food_project.controller;

import com.project.food_project.dto.CategoryExplorerDTO;
import com.project.food_project.services.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("/explorer")
    public ResponseEntity<?> getExplorerCategory(){
        List<CategoryExplorerDTO> categoryEntities = categoryServiceImp.getExplorerCategory();
        return new ResponseEntity<>(categoryEntities, HttpStatus.OK);
    }
    @GetMapping("/outstanding/{id}")
    public ResponseEntity<?> getOutstandingCategory(@PathVariable("id") int id){
        return new ResponseEntity<>(categoryServiceImp.getOutstandingCategory(id), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id){
        return new ResponseEntity<>(categoryServiceImp.getCategoryById(id), HttpStatus.OK);
    }

}
