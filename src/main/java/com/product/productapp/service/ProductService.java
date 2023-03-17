package com.product.productapp.service;


import com.product.productapp.dto.CreateProductRequestDto;
import com.product.productapp.dto.CreateProductResponseDto;
import com.product.productapp.dto.ProductDto;
import com.product.productapp.entity.Product;
import com.product.productapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto){
        Product product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .brand(requestDto.getBrand())
                .color(requestDto.getColor())
                .build();
        productRepository.save(product);

        return CreateProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();

    }

    public List<ProductDto> getAllProducts(){
        List<Product> allProducts = new ArrayList<>();
        productRepository.findAll().forEach(allProducts::add);
        return allProducts.stream()
                .map(
                        product -> ProductDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .brand(product.getBrand())
                                .price(product.getPrice())
                                .color(product.getColor())
                                .build()
                ).collect(Collectors.toList());
    }


}
