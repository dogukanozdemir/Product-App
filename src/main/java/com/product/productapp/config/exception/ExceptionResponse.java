package com.product.productapp.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {

    private String message;
    private LocalDateTime time;
}
