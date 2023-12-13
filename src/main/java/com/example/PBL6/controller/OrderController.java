package com.example.PBL6.controller;

import com.example.PBL6.dto.order.OrderDto;
import com.example.PBL6.dto.order.OrderItemResponseDto;
import com.example.PBL6.dto.order.OrderRequestDto;
import com.example.PBL6.dto.order.OrderResponseDto;
import com.example.PBL6.persistance.order.Order;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.service.OrderService;
import com.example.PBL6.util.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if (user != null) {
            Double amount = orderRequestDto.getAmount();
            String addressDelivery = orderRequestDto.getAddressDelivery();
            if (amount != null) {
                OrderDto orderDto;
                if(orderRequestDto.getProductId() != null) {
                    orderDto = orderService.saveOrderBuyNow(user, orderRequestDto, "UN-COMPLETE","COD");
                } else {
                    orderDto = orderService.saveOrder(user, "COD", amount, "UN-COMPLETE", addressDelivery);
                }
                if (orderDto != null) {
                    return ResponseEntity.ok(orderDto);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/getOrders")
    public ResponseEntity<Object> getAllOrders() {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if (user != null) {
            List<OrderResponseDto> orders = orderService.getAllOrders(user);
            if(orders == null || orders.isEmpty()) {
                return ResponseEntity.ok("Chưa có đơn hàng nào");
            } else {
                return ResponseEntity.ok(orders);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
