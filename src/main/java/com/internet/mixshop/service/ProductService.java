package com.internet.mixshop.service;

import com.internet.mixshop.model.Product;

public interface ProductService extends GenericService<Product, Long> {
    Product update(Product product);
}
