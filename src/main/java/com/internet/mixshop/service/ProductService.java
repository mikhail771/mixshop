package com.internet.mixshop.service;

import com.internet.mixshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product getById(Long productId);

    Product update(Product product);

    List<Product> getAllProducts();

    boolean deleteById(Long productId);
}
