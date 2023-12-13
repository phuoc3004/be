package com.example.PBL6.repository;

import com.example.PBL6.persistance.cart.Cart;
import com.example.PBL6.persistance.cart.CartItem;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query
    Optional<CartItem> getCartItemByCartAndProductVariant(Cart cart, ProductVariant productVariant);
    @Query
    List<CartItem> getCartItemsByCart(Cart cart);
    @Query
    void deleteAllByCart(Cart cart);
}
