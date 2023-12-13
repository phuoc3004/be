package com.example.PBL6.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Double amount;
    private String addressDelivery;
    private Integer productId;
    private String color;
    private String size;
    private Integer quantity;
}
