package com.example.PBL6.repository;

import com.example.PBL6.persistance.product.FavouriteProduct;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteProductRepository extends JpaRepository<FavouriteProduct, Integer> {

    @Query
    List<FavouriteProduct> getFavouriteProductsByUser(User user);

    @Query
    boolean existsFavouriteProductByUserAndProduct(User user, Product product);

    @Query
    void deleteAllByUser(User user);
}
