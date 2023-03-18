package com.product.productapp.product;

import com.product.productapp.authentication.AuthenticationUtil;
import com.product.productapp.dto.product.ProductRequestDto;
import com.product.productapp.dto.product.ProductResponseDto;
import com.product.productapp.entity.Client;
import com.product.productapp.entity.Product;
import com.product.productapp.repository.ProductRepository;
import com.product.productapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateProductTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthenticationUtil authenticationUtil;

    @Test
    void when_product_create_successful_then_return_responseDto() {
        Client client = Client.builder()
                .id(1L)
                .username("testClient")
                .build();
        when(authenticationUtil.getCurrentClient()).thenReturn(client);


        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("testProduct")
                .description("testDescription")
                .price(10.0)
                .brand("testBrand")
                .color("testColor")
                .build();
        when(productRepository.findByNameAndClientId(any(), any())).thenReturn(Optional.empty());

        Product product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .brand(requestDto.getBrand())
                .color(requestDto.getColor())
                .clientId(client.getId())
                .build();

        when(productRepository.save(any())).thenReturn(product);

        ProductResponseDto responseDto = productService.createProduct(requestDto);

        ProductResponseDto expectedResponseDto = ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        assertThat(expectedResponseDto).isEqualTo(responseDto);
    }

    @Test
    void when_product_already_exists_then_throw_ResponseStatusException() {
        // Mock authentication
        Client client = Client.builder()
                .id(1L)
                .username("testClient")
                .build();
        when(authenticationUtil.getCurrentClient()).thenReturn(client);

        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("testProduct")
                .description("testDescription")
                .price(10.0)
                .brand("testBrand")
                .color("testColor")
                .build();

        when(productRepository.findByNameAndClientId(requestDto.getName(), client.getId())).
                thenReturn(Optional.of(Product.builder().build()));

        assertThrows(ResponseStatusException.class, () -> productService.createProduct(requestDto), "You already own a product called testProduct");
    }
}

