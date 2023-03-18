package com.product.productapp.authentication;

import com.product.productapp.authentication.dto.AuthenticatedClient;
import com.product.productapp.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

    private final AuthenticationFacade authenticationFacade;

    public Long getClientId(){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        assert principal instanceof AuthenticatedClient;
        return ((AuthenticatedClient) principal).getClient().getId();
    }

    public Client getCurrentClient(){
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        assert principal instanceof AuthenticatedClient;
        return ((AuthenticatedClient) principal).getClient();
    }

}
