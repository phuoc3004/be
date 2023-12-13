package com.example.PBL6.service;

import com.example.PBL6.dto.cart.CartItemDetail;
import com.example.PBL6.dto.cart.CartRequestDto;
import com.example.PBL6.dto.cart.CartResponseDto;
import com.example.PBL6.persistance.cart.CartItem;
import com.example.PBL6.persistance.user.User;

import java.util.List;

public interface CartService {
    List<CartItemDetail> getAllCartItems(User user);

    CartResponseDto addToCart(User user, CartRequestDto cartRequestDto);
    CartResponseDto deleteCartItem(User user, Integer id);
    CartResponseDto deleteAllCartItems(User user);
}
