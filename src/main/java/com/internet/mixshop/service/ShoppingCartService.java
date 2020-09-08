package com.internet.mixshop.service;

import com.internet.mixshop.model.Product;
import com.internet.mixshop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
