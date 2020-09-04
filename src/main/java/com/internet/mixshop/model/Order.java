package com.internet.mixshop.model;

import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userid;

    public Order(List<Product> products, Long userid) {
        this.id = id;
        this.products = products;
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
