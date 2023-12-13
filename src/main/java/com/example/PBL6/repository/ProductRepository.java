package com.example.PBL6.repository;

import com.example.PBL6.persistance.product.Category;
import com.example.PBL6.persistance.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query
    Optional<Product> findProductByName(String name);

    @Query
    Product getById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product p " +
            "       SET p.price = :newPrice, " +
            "           p.description = :newDescription, " +
            "           p.category_id = :newCategoryId, " +
            "           p.image = :newImage " +
            "       WHERE p.id = :productId", nativeQuery = true)
    void updateProduct(
            @Param("productId") Integer productId,
            @Param("newPrice") Double newPrice,
            @Param("newDescription") String newDescription,
            @Param("newCategoryId") Integer newCategoryId,
            @Param("newImage") String newImage
    );
}
