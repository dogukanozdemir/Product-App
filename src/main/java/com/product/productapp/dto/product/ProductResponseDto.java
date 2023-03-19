package com.product.productapp.dto.product;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductResponseDto {

    private Long id;
    private Long clientId;
    private String name;
    private String description;
    private Double price;
    private String brand;
    private String color;

}
