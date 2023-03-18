package com.product.productapp.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRegisterRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
