package com.example.PBL6.repository;

import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.product.ProductVariant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

    @Modifying
    @Query(value = "INSERT INTO product_variants (product_id, color, size, quantity)\n" +
            "SELECT p.id, :color, :size, :quantity\n" +
            "FROM products p\n" +
            "WHERE p.id = :productId\n" +
            "AND NOT EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM product_variants pv\n" +
            "    WHERE pv.product_id = p.id\n" +
            "    AND pv.color = :color\n" +
            "    AND pv.size = :size\n" +
            ");\n", nativeQuery = true)
    void addProductVariantIfExistProduct(@Param("productId") Integer id,
                                         @Param("color") String color,
                                         @Param("size") String size,
                                         @Param("quantity") Integer quantity);

    @Query
    Integer countByProductIdAndColorAndSize(Integer productId, String color, String size);

    @Modifying
    @Query(value = "UPDATE product_variants " +
            "SET quantity = quantity + :quantityToAdd " +
            "WHERE product_id = :productId", nativeQuery = true)
    void addQuantity(@Param("productId") Integer productId,
                     @Param("quantityToAdd") Integer quantityToAdd);

    @Modifying
    @Query(value = "UPDATE product_variants " +
            "SET quantity = quantity - :quantityToSubtract " +
            "WHERE id = :productVariantId " +
            "AND quantity >= :quantityToSubtract", nativeQuery = true)
    void subtractQuantity(@Param("productVariantId") Integer productVariantId,
                          @Param("quantityToSubtract") Integer quantityToSubtract);

    @Query
    List<ProductVariant> getAllByProduct(Product product);

    @Query
    Optional<ProductVariant> getProductVariantByProductAndColorAndSize(Product product, String color, String size);

    @Query(value = "SELECT * " +
            "FROM product_variants " +
            "WHERE product_variants.product_id = :productId " +
            "AND product_variants.color LIKE %:color% " +
            "AND product_variants.size LIKE %:size%", nativeQuery = true)
    ProductVariant getByProductIdAndColorAndSize(@Param("productId") Integer productId,
                                                 @Param("color") String color,
                                                 @Param("size") String size);



}
