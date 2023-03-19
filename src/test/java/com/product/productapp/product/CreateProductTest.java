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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@MockitoSettings(strictness = Strictness.LENIENT)
class CreateProductTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthenticationUtil authenticationUtil;

    private Client client;
    private ProductRequestDto productRequestDto;

    @BeforeEach()
    void setup(){
        client = Client.builder()
                .id(1L)
                .username("testClient")
                .build();
        when(authenticationUtil.getCurrentClient()).thenReturn(client);

        productRequestDto = ProductRequestDto.builder()
                .name("testProduct")
                .description("testDescription")
                .price(10.0)
                .brand("testBrand")
                .color("testColor")
                .build();

    }

    @Test
    void when_product_create_successful_then_return_responseDto() {
        when(productRepository.findByNameAndClientId(any(), any())).thenReturn(Optional.empty());

        Product product = Product.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .brand(productRequestDto.getBrand())
                .color(productRequestDto.getColor())
                .clientId(client.getId())
                .build();

        when(productRepository.save(any())).thenReturn(product);

        ProductResponseDto responseDto = productService.createProduct(productRequestDto);

        ProductResponseDto expectedResponseDto = ProductResponseDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .color(product.getColor())
                .price(product.getPrice())
                .brand(product.getBrand())
                .clientId(product.getClientId())
                .build();

        assertThat(expectedResponseDto).isEqualTo(responseDto);
    }

    @Test
    void when_product_already_exists_then_throw_ResponseStatusException() {
        when(productRepository.findByNameAndClientId(productRequestDto.getName(), client.getId())).
                thenReturn(Optional.of(Product.builder().build()));

        assertThrows(ResponseStatusException.class, () -> productService.createProduct(productRequestDto), "You already own a product called testProduct");
    }
}

