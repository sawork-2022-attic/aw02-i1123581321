package com.example.poshell.service;

import com.example.poshell.dao.entity.Product;
import com.example.poshell.dao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService{
    private final ProductRepository repository;
    private final Map<Product, Integer> cart = new HashMap<>();

    @Autowired
    CartServiceImpl(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public Iterable<Product> products() {
        return repository.findAll();
    }

    @Override
    public void resetCart() {
        cart.clear();
    }

    private Product checkArgument(String id, int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Item amount must be a positive number");
        }
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("product id does not exist"));
    }

    @Override
    public void addProduct(String id, int amount) {
        var product = checkArgument(id, amount);
        cart.merge(product, amount, Integer::sum);
    }

    @Override
    public void removeProduct(String id) {
        var product = checkArgument(id, 1);
        cart.remove(product);
    }

    @Override
    public void modifyCart(String id, int amount) {
        var product = checkArgument(id, amount);
        cart.put(product, amount);
    }

    @Override
    public Set<Map.Entry<Product, Integer>> content() {
        return cart.entrySet();
    }
}
