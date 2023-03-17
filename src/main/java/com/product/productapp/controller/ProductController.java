package com.product.productapp.controller;

import com.product.productapp.dto.CreateProductRequestDto;
import com.product.productapp.dto.CreateProductResponseDto;
import com.product.productapp.dto.ProductDto;
import com.product.productapp.entity.Product;
import com.product.productapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path ="/product")
    public ResponseEntity<CreateProductResponseDto> createProduct(@Validated @RequestBody CreateProductRequestDto createProductRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductRequestDto));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductDto>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }


}
