package com.example.poshell.service;

import com.example.poshell.dao.entity.Product;

import java.util.Map;
import java.util.Set;

public interface CartService {
    Iterable<Product> products();
    Set<Map.Entry<Product, Integer>> content();


    void resetCart();

    void addProduct(String id, int amount);
    void removeProduct(String id);
    void modifyCart(String id, int amount);
}
