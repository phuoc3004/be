package com.example.PBL6.controller;

import com.example.PBL6.dto.product.ProductRequestDto;
import com.example.PBL6.dto.product.ProductResponseDto;
import com.example.PBL6.dto.product.UpdateProductDto;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/all")
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getDetailProduct(@PathVariable("id") Integer id) {
        ProductResponseDto productResponseDto = productService.getDetailProduct(id);
        if(productResponseDto != null) {
            return ResponseEntity.ok(productResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Integer id, @RequestBody UpdateProductDto updateProductDto) {
        ProductResponseDto product = productService.updateProduct(id, updateProductDto);
        if(product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }
}
