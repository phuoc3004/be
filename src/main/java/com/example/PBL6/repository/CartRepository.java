package com.example.PBL6.repository;

import com.example.PBL6.persistance.cart.Cart;
import com.example.PBL6.persistance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query
    boolean existsCartByUser(User user);

    @Query
    Cart getCartByUser(User user);
}
