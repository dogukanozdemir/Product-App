package com.product.productapp.authentication.dto;

import com.product.productapp.entity.Client;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthenticatedClient extends User {

    private final Client client;

    public AuthenticatedClient(String username, String password, Collection<? extends GrantedAuthority> authorities, Client client) {
        super(username, password, authorities);
        this.client = client;
    }
}
