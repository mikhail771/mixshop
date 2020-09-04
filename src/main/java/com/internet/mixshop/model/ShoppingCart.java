package com.internet.mixshop.model;

import java.util.List;

public class ShoppingCart {
    private long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(List<Product> products, Long userId) {
        this.products = products;
        userId = userId;
    }

    public void setId(long shoppingCartId) {
        this.id = shoppingCartId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        userId = userId;
    }
}
