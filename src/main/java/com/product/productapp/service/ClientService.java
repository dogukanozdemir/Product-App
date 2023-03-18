package com.product.productapp.service;

import com.product.productapp.authentication.jwt.JwtProvider;
import com.product.productapp.dto.client.CreateClientRequestDto;
import com.product.productapp.dto.client.CreateClientResponseDto;
import com.product.productapp.dto.client.LoginClientRequestDto;
import com.product.productapp.dto.client.LoginClientResponseDto;
import com.product.productapp.entity.Client;
import com.product.productapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    public CreateClientResponseDto registerClient(CreateClientRequestDto requestDto) {
        if (clientRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("User with '%s' username already exists", requestDto.getUsername()));
        }

        Client client = Client.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        clientRepository.save(client);

        return CreateClientResponseDto.builder()
                .username(client.getUsername())
                .hashedPassword(client.getPassword())
                .build();
    }

    public LoginClientResponseDto loginClient(LoginClientRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        if (authentication.isAuthenticated()) {
            String jwtToken = jwtProvider.generateToken(requestDto.getUsername());
            return LoginClientResponseDto.builder()
                    .username(requestDto.getUsername())
                    .jwtToken(jwtToken)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

}
