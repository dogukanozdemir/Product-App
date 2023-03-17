package com.product.productapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e)
            throws Exception {
        if (e instanceof ResponseStatusException rse) {
            log.error("Rest Global ErrorHandler: ", e);
            res.sendError(rse.getStatusCode().value(),rse.getReason());
            return;
        }
        log.error("Rest Global ErrorHandler: ", e);
        res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "system.error");
    }
}
