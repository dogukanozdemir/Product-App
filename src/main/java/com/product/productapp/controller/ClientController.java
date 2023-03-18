package com.product.productapp.controller;

import com.product.productapp.dto.client.CreateClientRequestDto;
import com.product.productapp.dto.client.CreateClientResponseDto;
import com.product.productapp.dto.client.LoginClientRequestDto;
import com.product.productapp.dto.client.LoginClientResponseDto;
import com.product.productapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(path = "/register")
    public ResponseEntity<CreateClientResponseDto> registerClient(@Validated @RequestBody CreateClientRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.registerClient(requestDto));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginClientResponseDto> loginClient(@Validated @RequestBody LoginClientRequestDto requestDto){
        return ResponseEntity.ok(clientService.loginClient(requestDto));
    }

}
