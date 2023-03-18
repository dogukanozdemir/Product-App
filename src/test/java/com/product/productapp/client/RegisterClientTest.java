package com.product.productapp.client;

import com.product.productapp.dto.client.ClientRegisterRequestDto;
import com.product.productapp.dto.client.ClientRegisterResponseDto;
import com.product.productapp.entity.Client;
import com.product.productapp.repository.ClientRepository;
import com.product.productapp.service.ClientService;
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

    @Test
    void testRegisterClient_Success() {

        String username = "name";
        String password = "password";
        ClientRegisterRequestDto requestDto = ClientRegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        when(clientRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");


        ClientRegisterResponseDto responseDto = clientService.registerClient(requestDto);

        assertThat(username).isEqualTo(responseDto.getUsername());
        assertThat("encodedPassword").isEqualTo(responseDto.getHashedPassword());
    }

    @Test
    void testRegisterClient_UsernameAlreadyExists() {

        String username = "name";
        String password = "password";
        ClientRegisterRequestDto requestDto = ClientRegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        Client existingClient = Client.builder()
                .username(username)
                .password("encodedPassword")
                .build();

        when(clientRepository.findByUsername(username)).thenReturn(Optional.of(existingClient));

        assertThrows(ResponseStatusException.class,
                () -> clientService.registerClient(requestDto),"Client with 'name' username already exists");

    }


}
