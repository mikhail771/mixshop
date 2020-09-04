package com.internet.mixshop.dao.impl;

import com.internet.mixshop.dao.ProductDao;
import com.internet.mixshop.db.Storage;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> getById(Long productId) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(i -> Storage.products.get(i).getId().equals(product.getId()))
                .forEach(i -> Storage.products.set(i, product));
        return product;
    }

    @Override
    public boolean delete(Long productId) {
        return Storage.products.removeIf(product -> product.getId().equals(productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return Storage.products;
    }
}
