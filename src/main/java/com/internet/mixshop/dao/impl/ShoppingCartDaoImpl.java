package com.internet.mixshop.dao.impl;

import com.internet.mixshop.dao.ShoppingCartDao;
import com.internet.mixshop.db.Storage;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getById(Long shoppingCartId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(shoppingCartId))
                .findFirst();
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> Storage.shoppingCarts.get(i).getId().equals(shoppingCart.getId()))
                .forEach(i -> Storage.shoppingCarts.set(i, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean delete(Long shoppingCartId) {
        return Storage.shoppingCarts
                .removeIf(shoppingCart -> shoppingCart.getId().equals(shoppingCartId));
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }
}
