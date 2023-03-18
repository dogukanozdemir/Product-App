package com.product.productapp.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NonNull
    private String name;

    private String description;

    @NonNull
    private Double price;

    private String brand;

    private String color;
}
