package com.product.productapp.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String brand;
    private String color;

}
