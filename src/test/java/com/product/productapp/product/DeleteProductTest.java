package com.product.productapp.product;

import com.product.productapp.authentication.AuthenticationUtil;
import com.product.productapp.entity.Client;
import com.product.productapp.entity.Product;
import com.product.productapp.repository.ProductRepository;
import com.product.productapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@MockitoSettings(strictness = Strictness.LENIENT)
class DeleteProductTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private AuthenticationUtil authenticationUtil;

    @Mock
    private ProductRepository productRepository;

    @Test
    void when_product_deletion_success_then_return_success_string() {
        Client client = Client.builder()
                .id(1L)
                .username("dogukan")
                .build();

        Product product = Product.builder()
                .id(2L)
                .name("name")
                .description("description")
                .clientId(client.getId())
                .build();

        when(authenticationUtil.getCurrentClient()).thenReturn(client);
        when(productRepository.findByIdAndAndClientId(anyLong(), anyLong())).thenReturn(Optional.of(product));

        String result = productService.deleteProductById(2L);
        assertThat(result).isEqualTo("Product with id 2 has been deleted successfully");
    }

    @Test
    void when_product_not_found_then_throw_ResponseStatusException() {
        Client client = Client.builder()
                .id(1L)
                .username("dogukan")
                .build();

        when(authenticationUtil.getCurrentClient()).thenReturn(client);
        when(productRepository.findByIdAndAndClientId(anyLong(), anyLong())).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class,
                () -> productService.deleteProductById(2L),
                "Current Client dogukan, doesn't have a product with id 2");

    }
}
