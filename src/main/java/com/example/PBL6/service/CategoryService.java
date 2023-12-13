package com.example.PBL6.service;

import com.example.PBL6.dto.category.CategoryDto;
import com.example.PBL6.persistance.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategorise();

    Category addCategory(CategoryDto categoryDto);

    void deleteCategory(Integer id);
}
