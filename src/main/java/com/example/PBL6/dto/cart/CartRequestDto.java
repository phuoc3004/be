package com.example.PBL6.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    private Integer productId;
    private Integer quantity;
    private String color;
    private String size;
}
