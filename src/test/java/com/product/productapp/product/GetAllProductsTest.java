package com.product.productapp.product;

import com.product.productapp.dto.product.ProductResponseDto;
import com.product.productapp.entity.Product;
import com.product.productapp.repository.ProductRepository;
import com.product.productapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class GetAllProductsTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void when_get_all_products_return_all_products() {

        Product product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("This is product 1")
                .brand("Brand 1")
                .price(10.00)
                .color("Red")
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("This is product 2")
                .brand("Brand 2")
                .price(20.00)
                .color("Blue")
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);


        when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponseDto> expectedProducts = new ArrayList<>();
        expectedProducts.add(ProductResponseDto.builder()
                .id(1L)
                .name("Product 1")
                .description("This is product 1")
                .brand("Brand 1")
                .price(10.00)
                .color("Red")
                .build());
        expectedProducts.add(ProductResponseDto.builder()
                .id(2L)
                .name("Product 2")
                .description("This is product 2")
                .brand("Brand 2")
                .price(20.00)
                .color("Blue")
                .build());

        List<ProductResponseDto> actualProducts = productService.getAllProducts();
        assertThat(expectedProducts).hasSameSizeAs(actualProducts);
        for (int i = 0; i < expectedProducts.size(); i++) {
            assertThat(expectedProducts.get(i)).isEqualTo(actualProducts.get(i));
        }
    }
}
