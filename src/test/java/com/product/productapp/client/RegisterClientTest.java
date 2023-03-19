package com.product.productapp.client;

import com.product.productapp.dto.client.ClientRegisterRequestDto;
import com.product.productapp.dto.client.ClientRegisterResponseDto;
import com.product.productapp.entity.Client;
import com.product.productapp.repository.ClientRepository;
import com.product.productapp.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class RegisterClientTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ClientRegisterRequestDto requestDto;

    @BeforeEach()
    void setup(){
        requestDto = ClientRegisterRequestDto.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    void when_credentials_valid_then_register_successfully() {

        when(clientRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");


        ClientRegisterResponseDto response= clientService.registerClient(requestDto);

        assertThat(response)
                .isEqualTo(ClientRegisterResponseDto.builder()
                        .message("Client username registered to system successfully")
                        .build()
                );
    }

    @Test
    void when_username_already_exists_then_throw_ResponseStatusException() {

        Client existingClient = Client.builder()
                .username("username")
                .password("encodedPassword")
                .build();

        when(clientRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.of(existingClient));

        assertThrows(ResponseStatusException.class,
                () -> clientService.registerClient(requestDto),"Client with 'username' username already exists");

    }


}
