package com.example.PBL6.service.impl;

import com.example.PBL6.dto.cart.CartItemDetail;
import com.example.PBL6.dto.cart.CartRequestDto;
import com.example.PBL6.dto.cart.CartResponseDto;
import com.example.PBL6.persistance.cart.Cart;
import com.example.PBL6.persistance.cart.CartItem;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.product.ProductVariant;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.repository.*;
import com.example.PBL6.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartItemDetail> getAllCartItems(User user) {
        List<CartItemDetail> cartItemDetails = new ArrayList<>();
        Cart cart = cartRepository.getCartByUser(user);
        List<CartItem> cartItems = cartItemRepository.getCartItemsByCart(cart);
        for(CartItem cartItem : cartItems) {
            CartItemDetail cartItemDetail = new CartItemDetail()
                    .builder()
                    .id(cartItem.getId())
                    .productId(cartItem.getProductVariant().getProduct().getId())
                    .productVariantId(cartItem.getProductVariant().getId())
                    .size(cartItem.getProductVariant().getSize())
                    .color(cartItem.getProductVariant().getColor())
                    .image(cartItem.getProductVariant().getProduct().getImage())
                    .productName(cartItem.getProductVariant().getProduct().getName())
                    .price(cartItem.getProductVariant().getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            cartItemDetails.add(cartItemDetail);
        }
        if(cartItemDetails.isEmpty()) {
            return null;
        }
        return cartItemDetails;
    }

    @Override
    @Transactional
    public CartResponseDto addToCart(User user, CartRequestDto cartRequestDto) {
        Product product = productRepository.getById(cartRequestDto.getProductId()); // Lấy product thêm vào cart
        Optional<ProductVariant> productVariant = productVariantRepository.getProductVariantByProductAndColorAndSize(
                product, cartRequestDto.getColor(), cartRequestDto.getSize());               // Lấy product_variant
        if (!productVariant.isPresent()) {
            return new CartResponseDto("Sản phẩm tạm thời hết hàng");
        } else {
            Cart cart;
            if (!cartRepository.existsCartByUser(user)) {  // check xem cart của user có chưa
                cart = new Cart().builder()
                        .createDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .user(user)
                        .build();
                cartRepository.save(cart);                // lưu cart mới vào db
                CartItem cartItem = new CartItem().builder()
                        .productVariant(productVariant.get())
                        .quantity(cartRequestDto.getQuantity())
                        .cart(cart)
                        .build();
                cartItemRepository.save(cartItem);        // lưu cart_item mới
                return new CartResponseDto("thêm thành công");
            } else {
                cart = cartRepository.getCartByUser(user);  // lấy cart của user
                Optional<CartItem> cartItem = cartItemRepository.getCartItemByCartAndProductVariant(cart, productVariant.get()); // lấy cartItem của user
                if (cartItem.isPresent()) {  // nếu có cartItem trong kho
                    if (cartItem.get().getQuantity() + cartRequestDto.getQuantity() > productVariant.get().getQuantity()) {
                        return new CartResponseDto("sản phẩm không đủ");
                    } else {
                        cartItem.get().setQuantity(cartItem.get().getQuantity() + cartRequestDto.getQuantity());
                        cartItemRepository.save(cartItem.get());    // lưu cartItem nếu như đủ số lượng còn
                        return new CartResponseDto("thêm thành công");
                    }

                } else {
                    if (cartRequestDto.getQuantity() > productVariant.get().getQuantity()) {
                        return new CartResponseDto("sản phẩm không đủ");
                    } else {
                        CartItem newCartItem = new CartItem().builder()
                                .cart(cart)
                                .productVariant(productVariant.get())
                                .quantity(cartRequestDto.getQuantity())
                                .build();
                        cartItemRepository.save(newCartItem);   // lưu cartItem nếu như đủ số lượng còn
                        return new CartResponseDto("thêm thành công");
                    }
                }
            }
        }
    }
    @Override
    @Transactional
    public CartResponseDto deleteCartItem(User user, Integer id) {
        cartItemRepository.deleteById(id);
        return new CartResponseDto("Xóa thành công");
    }

    @Override
    @Transactional
    public CartResponseDto deleteAllCartItems(User user) {
        Cart cart = cartRepository.getCartByUser(user);
        cartItemRepository.deleteAllByCart(cart);
        return new CartResponseDto("Xóa toàn bộ giỏ hàng thành công");
    }
}
