package com.example.PBL6.dto.product;

import com.example.PBL6.persistance.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaProductRespDto {
    private Integer id;
    private Product product;
}
