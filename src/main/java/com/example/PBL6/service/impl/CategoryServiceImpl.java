package com.example.PBL6.service.impl;

import com.example.PBL6.dto.category.CategoryDto;
import com.example.PBL6.persistance.product.Category;
import com.example.PBL6.repository.CategoryRepository;
import com.example.PBL6.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategorise() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category addCategory(CategoryDto categoryDto) {
        Category newCategory = Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.delete(categoryRepository.getById(id));
    }
}
