package com.example.poshell.cli;

import com.example.poshell.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {
    private final CartService cartService;

    private String cartToString() {
        var s = cartService.content();
        if (s.isEmpty()) {
            return "Empty Cart";
        }
        var builder = new StringBuilder();
        double sum = 0;
        builder.append("Cart -----------------\n");
        for (var e : s) {
            builder.append(e.getKey().toString())
                    .append("\t")
                    .append(e.getValue())
                    .append("\n");
            sum += e.getValue() * e.getKey().getPrice();
        }
        builder.append("----------------------\n")
                .append("Total...\t\t\t")
                .append(sum);
        return builder.toString();
    }

    @Autowired
    PosCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        var builder = new StringBuilder();
        int i = 1;
        for (var product : cartService.products()) {
            builder.append("\t")
                    .append(i)
                    .append("\t")
                    .append(product.toString())
                    .append("\n");
            i++;
        }
        return builder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        cartService.resetCart();
        return cartToString() + " OK";
    }

    @ShellMethod(value = "Add a product", key = "a")
    public String add(String id, int amount) {
        try {
            cartService.addProduct(id, amount);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return cartToString();
    }

    @ShellMethod(value = "Remove a product", key = "r")
    public String remove(String id) {
        try {
            cartService.removeProduct(id);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return cartToString();
    }


    @ShellMethod(value = "Empty the cart", key = "e")
    public String emptyCart() {
        cartService.resetCart();
        return cartToString();
    }

    @ShellMethod(value = "Print cart content", key = "c")
    public String printCart() {
        return cartToString();
    }

    @ShellMethod(value = "Modify the cart", key = "m")
    public String modifyCart(String id, int amount) {
        try {
            cartService.modifyCart(id, amount);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return cartToString();
    }
}
