package com.product.productapp.controller;

import com.product.productapp.dto.product.ProductRequestDto;
import com.product.productapp.dto.product.ProductResponseDto;
import com.product.productapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping(path ="/product")
    public ResponseEntity<ProductResponseDto> createProduct(@Validated @RequestBody ProductRequestDto productRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequestDto));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping(path = "/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.deleteProductById(id));
    }

    @PutMapping(path = "/product/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@Validated @RequestBody ProductRequestDto productRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(productService.updateProductById(productRequestDto,id));
    }

}
