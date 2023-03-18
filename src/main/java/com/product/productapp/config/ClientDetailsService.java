package com.product.productapp.config;

import com.product.productapp.authentication.dto.AuthenticatedClient;
import com.product.productapp.entity.Client;
import com.product.productapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Invalid Username: " + username)
                );
        return new AuthenticatedClient(
                username,
                client.getPassword(),
                List.of(),
                client
        );
    }
}
