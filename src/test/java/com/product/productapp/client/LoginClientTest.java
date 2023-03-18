package com.product.productapp.client;

import com.product.productapp.authentication.jwt.JwtProvider;
import com.product.productapp.dto.client.ClientLoginRequestDto;
import com.product.productapp.dto.client.ClientLoginResponseDto;
import com.product.productapp.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class LoginClientTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationException authenticationException;

    private ClientLoginRequestDto clientLoginRequestDto;

    @BeforeEach
    void setup(){
        clientLoginRequestDto = ClientLoginRequestDto.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    void when_client_login_success() {

        String encodedPassword = "encodedPassword";

        when(authentication.isAuthenticated()).thenReturn(true);

        String jwtToken = "jwtToken";
        when(jwtProvider.generateToken(clientLoginRequestDto.getUsername())).thenReturn(jwtToken);

        when(passwordEncoder.encode(clientLoginRequestDto.getPassword())).thenReturn(encodedPassword);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientLoginRequestDto.getUsername(), clientLoginRequestDto.getPassword()))).thenReturn(authentication);

        ClientLoginResponseDto responseDto = clientService.loginClient(clientLoginRequestDto);

        assertThat(clientLoginRequestDto.getUsername()).isEqualTo(responseDto.getUsername());
        assertThat(jwtToken).isEqualTo(responseDto.getJwtToken());

    }

    @Test
    void when_authentication_manager_fail_then_throw_AuthenticationException() {
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientLoginRequestDto.getUsername(), clientLoginRequestDto.getPassword())))
                .thenThrow(authenticationException);

        assertThrows(AuthenticationException.class,
                () -> clientService.loginClient(clientLoginRequestDto));
    }

    @Test
    void when_client_not_authentication_then_throw_ResponseStatusException() {

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientLoginRequestDto.getUsername(), clientLoginRequestDto.getPassword()))).thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> clientService.loginClient(clientLoginRequestDto), "Invalid username or password");
    }

}
