package com.product.productapp.repository;

import com.product.productapp.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findByIdAndAndClientId(Long id, Long clientId);

    Optional<Product> findByNameAndClientId(String name, Long clientId);
}
