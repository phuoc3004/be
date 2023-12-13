package com.example.PBL6.service;

import com.example.PBL6.dto.order.OrderDto;
import com.example.PBL6.dto.order.OrderRequestDto;
import com.example.PBL6.dto.order.OrderResponseDto;
import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.user.User;

import java.util.List;

public interface OrderService {
    OrderDto saveOrder(User user, String paymentMethod, double totalPrice, String status, String addressDelivery);

    OrderDto saveOrderBuyNow(User user, OrderRequestDto orderRequestDto, String status, String paymentMethod);

    List<OrderResponseDto> getAllOrders(User user);
}
