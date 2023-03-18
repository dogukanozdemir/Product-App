package com.product.productapp.controller;

import com.product.productapp.dto.client.ClientRegisterRequestDto;
import com.product.productapp.dto.client.ClientRegisterResponseDto;
import com.product.productapp.dto.client.ClientLoginRequestDto;
import com.product.productapp.dto.client.ClientLoginResponseDto;
import com.product.productapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ClientRegisterResponseDto> registerClient(@Validated @RequestBody ClientRegisterRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.registerClient(requestDto));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ClientLoginResponseDto> loginClient(@Validated @RequestBody ClientLoginRequestDto requestDto){
        return ResponseEntity.ok(clientService.loginClient(requestDto));
    }

}
