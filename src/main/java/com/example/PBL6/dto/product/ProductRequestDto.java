package com.example.PBL6.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    public String name;
    public String description;
    public Double price;
    public Integer discount;
    public Integer quantity;
    public String color;
    public String size;
    public Integer categoryId;
    public String imageUrl;
}
