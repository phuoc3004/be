package com.example.PBL6.controller;

import com.example.PBL6.dto.cart.CartItemDetail;
import com.example.PBL6.dto.cart.CartRequestDto;
import com.example.PBL6.dto.cart.CartResponseDto;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.service.CartService;
import com.example.PBL6.service.UserService;
import com.example.PBL6.util.AuthenticationUtils;
import com.example.PBL6.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        User user =  userService.getUserProfile(JwtUtils.getUserEmailFromJwt(token)).orElse(null);
        if(user != null) {
            List<CartItemDetail> cartItemDetails =  cartService.getAllCartItems(user);
            return ResponseEntity.ok(cartItemDetails);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't find user");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                     @RequestBody CartRequestDto cartRequestDto) {
        String email = JwtUtils.getUserEmailFromJwt(token);
        User user =  userService.getUserProfile(email).orElse(null);
        if(user != null) {
            CartResponseDto cartResponseDto = cartService.addToCart(user, cartRequestDto);
            return ResponseEntity.ok(cartResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't find user");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable("id") Integer id) {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            CartResponseDto cartResponseDto = cartService.deleteCartItem(user, id);
            return ResponseEntity.ok(cartResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/deleteAllCartItems")
    public ResponseEntity<Object> deleteAllCartItems() {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            CartResponseDto cartResponseDto = cartService.deleteAllCartItems(user);
            return ResponseEntity.ok(cartResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
