package com.example.PBL6.service;

import com.example.PBL6.dto.product.*;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();

    ProductResponseDto addProduct(ProductRequestDto productRequestDto);

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getDetailProduct(Integer id);

    FaProductRespMesDto addFavouriteProduct(User user, Integer id);

    List<FaProductRespDto> getFavouriteProducts(User user);

    FaProductRespMesDto deleteFavouriteProduct(User user, Integer id);

    FaProductRespMesDto deleteAllFavouriteProducts(User user);

    ProductResponseDto updateProduct(Integer id, UpdateProductDto updateProductDto);

}
