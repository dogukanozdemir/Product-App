package com.product.productapp.product;

import com.product.productapp.authentication.AuthenticationUtil;
import com.product.productapp.dto.product.ProductResponseDto;
import com.product.productapp.dto.product.ProductRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class UpdateProductTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AuthenticationUtil authenticationUtil;

    private Client client;
    private ProductRequestDto updatedProductRequestDto;

    @BeforeEach
    void setup(){
        client = Client.builder()
                .id(1L)
                .username("name")
                .build();
        when(authenticationUtil.getCurrentClient()).thenReturn(client);

        updatedProductRequestDto = ProductRequestDto.builder()
                .name("Updated Product")
                .description("updated description")
                .brand("Brand 2")
                .price(20.00)
                .color("Blue")
                .build();
    }


    @Test
    void when_product_is_found_then_update_product() {

        Product existingProduct = Product.builder()
                .id(1L)
                .name("product")
                .description("description")
                .brand("Brand")
                .price(10.00)
                .color("Red")
                .clientId(client.getId())
                .build();

        when(productRepository.findByIdAndAndClientId(anyLong(), anyLong())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        ProductResponseDto expectedProduct = ProductResponseDto.builder()
                .id(existingProduct.getId())
                .name(updatedProductRequestDto.getName())
                .description(updatedProductRequestDto.getDescription())
                .brand(updatedProductRequestDto.getBrand())
                .price(updatedProductRequestDto.getPrice())
                .color(updatedProductRequestDto.getColor())
                .clientId(client.getId())
                .build();

        ProductResponseDto actualProduct = productService.updateProductById(updatedProductRequestDto, existingProduct.getId());
        assertThat(expectedProduct).isEqualTo(actualProduct);
    }

    @Test
    void when_product_not_found_then_throw_ResponseStatusException() {

        when(productRepository.findByIdAndAndClientId(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> productService.updateProductById(updatedProductRequestDto,2L),
                "Product with id 2 was not found");
    }
}
