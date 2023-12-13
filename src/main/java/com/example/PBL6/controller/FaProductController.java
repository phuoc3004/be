package com.example.PBL6.controller;

import com.example.PBL6.dto.product.FaProductRespDto;
import com.example.PBL6.dto.product.FaProductRespMesDto;
import com.example.PBL6.persistance.product.Product;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.service.ProductService;
import com.example.PBL6.util.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favouriteProduct")
public class FaProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Object> getAllFavouriteProducts() {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            List<FaProductRespDto> faProductRespDtos = productService.getFavouriteProducts(user);
            if(faProductRespDtos == null) {
                return ResponseEntity.ok("Không có sản phẩm yêu thích nào");
            } else {
                return ResponseEntity.ok(faProductRespDtos);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Object> addFavouriteProduct(@PathVariable("id") Integer id) {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            FaProductRespMesDto faProductRespMesDto = productService.addFavouriteProduct(user, id);
            return ResponseEntity.ok(faProductRespMesDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteFavouriteProduct(@PathVariable("id") Integer id) {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            FaProductRespMesDto faProductRespMesDto = productService.deleteFavouriteProduct(user, id);
            return ResponseEntity.ok(faProductRespMesDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteFavouriteProduct() {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if(user != null) {
            FaProductRespMesDto faProductRespMesDto = productService.deleteAllFavouriteProducts(user);
            return ResponseEntity.ok(faProductRespMesDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
