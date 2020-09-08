package com.internet.mixshop.service.impl;

import com.internet.mixshop.dao.ShoppingCartDao;
import com.internet.mixshop.db.Storage;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.service.ShoppingCartService;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        ShoppingCart sc = shoppingCartDao.getById(shoppingCart.getId()).get();
        sc.getProducts().remove(product);
        shoppingCartDao.update(sc);
        return true;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart.getId());
    }
}
