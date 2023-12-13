package com.example.PBL6.dto.order;

import com.example.PBL6.persistance.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer orderId;

    @JsonIgnore
    private List<OrderItem> orderItems;

    private Double totalPrice;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private String status;
    private String address;
}
