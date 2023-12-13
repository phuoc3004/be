package com.example.PBL6.repository;

import com.example.PBL6.persistance.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();
    Category getById(Integer id);

}
