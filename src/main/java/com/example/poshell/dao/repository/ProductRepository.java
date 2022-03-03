package com.example.poshell.dao.repository;


import com.example.poshell.dao.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    @Override
    Optional<Product> findById(String s);

    @Override
    Iterable<Product> findAll();
}
