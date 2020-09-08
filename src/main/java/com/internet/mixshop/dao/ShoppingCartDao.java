package com.internet.mixshop.dao;

import com.internet.mixshop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getById(Long shoppingCartId);

    Optional<ShoppingCart> getByUserId(Long userId);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(Long shoppingCartId);

    List<ShoppingCart> getAll();
}
