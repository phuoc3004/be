package com.example.PBL6.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryId;
}
