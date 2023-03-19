package com.product.productapp.config.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e){
        log.info("Rest Global Handler: ", e);
        if(e instanceof ResponseStatusException rse){
            return new ResponseEntity<>(
                            ExceptionResponse.builder()
                                    .message(e.getMessage())
                                    .time(LocalDateTime.now())
                                    .build(),
                            rse.getStatusCode());
        } else if(e instanceof AuthenticationException){
            return new ResponseEntity<>(
                    ExceptionResponse.builder()
                            .message("No or Invalid token was provided, please try to login again or make sure you provided a valid token.")
                            .time(LocalDateTime.now())
                            .build(),
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(e.getMessage())
                        .time(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
