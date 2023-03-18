package com.product.productapp.service;

import com.product.productapp.authentication.AuthenticationUtil;
import com.product.productapp.dto.product.ProductRequestDto;
import com.product.productapp.dto.product.ProductResponseDto;
import com.product.productapp.dto.product.ProductDto;
import com.product.productapp.entity.Client;
import com.product.productapp.entity.Product;
import com.product.productapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final AuthenticationUtil authenticationUtil;

    public ProductResponseDto createProduct(ProductRequestDto requestDto){
        Client currentClient = authenticationUtil.getCurrentClient();
        productRepository.findByNameAndClientId(requestDto.getName(), currentClient.getId())
                .ifPresent(product -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already own a product called " + requestDto.getName());
                });

        Product product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .brand(requestDto.getBrand())
                .color(requestDto.getColor())
                .createdAt(LocalDateTime.now())
                .clientId(currentClient.getId())
                .build();
        productRepository.save(product);

        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice());
    }


    public String deleteProductById(Long id) {
        Client currentClient = authenticationUtil.getCurrentClient();

        Product product = productRepository.findByIdAndAndClientId(id, currentClient.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Current Client %s, doesn't have a product with id %s",
                                currentClient.getUsername(), id)));

        productRepository.delete(product);
        return String.format("Product with id %s has been deleted successfully", id);
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
                ).toList();
    }

    public ProductDto updateProductById(ProductRequestDto requestDto, Long id) {
        Client client = authenticationUtil.getCurrentClient();
        Product product = productRepository.findByIdAndAndClientId(id, client.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Product with id %s was not found", id)));

        BeanUtils.copyProperties(requestDto, product, "id", "createdAt");
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .price(product.getPrice())
                .color(product.getColor())
                .build();
    }


}
