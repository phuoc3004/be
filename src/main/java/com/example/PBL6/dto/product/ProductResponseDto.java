package com.example.PBL6.dto.product;

import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Product product;
    private List<ProductVariant> productVariants;
}
