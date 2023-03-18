package com.product.productapp.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private Double price;

    private String brand;

    private String color;
}
