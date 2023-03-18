package com.product.productapp.repository;

import com.product.productapp.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Long> {

    Optional<Client> findByUsername(String username);
}
