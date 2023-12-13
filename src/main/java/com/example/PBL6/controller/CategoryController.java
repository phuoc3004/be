package com.example.PBL6.controller;

import com.example.PBL6.dto.category.CategoryDto;
import com.example.PBL6.persistance.product.Category;
import com.example.PBL6.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategorise();
    }

    @PostMapping("/add")
    public Category addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PostMapping ("/update/{id}")
    public Category updateCategory(@RequestParam("id") Integer id) {
        // continue
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") Integer id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("compelete delete");
        } catch (Exception e) {
            return ResponseEntity.ok("fail to delete");
        }
    }
}
