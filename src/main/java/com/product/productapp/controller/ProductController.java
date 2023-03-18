package com.product.productapp.controller;

import com.product.productapp.authentication.AuthenticationUtil;
import com.product.productapp.dto.product.CreateProductRequestDto;
import com.product.productapp.dto.product.CreateProductResponseDto;
import com.product.productapp.dto.product.ProductDto;
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
    public ResponseEntity<CreateProductResponseDto> createProduct(@Validated @RequestBody CreateProductRequestDto createProductRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductRequestDto));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductDto>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping(path = "/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.deleteProductById(id));
    }

    @PutMapping(path = "/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Validated @RequestBody CreateProductRequestDto createProductRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(productService.updateProductById(createProductRequestDto,id));
    }

}
