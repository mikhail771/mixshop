package com.internet.mixshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userid;

    public Order(Long userid) {
        this.products = new ArrayList<>();
        this.userid = userid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", products=" + products
                + ", userid=" + userid
                + '}';
    }
}
