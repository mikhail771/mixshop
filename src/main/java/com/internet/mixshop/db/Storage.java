package com.internet.mixshop.db;

import com.internet.mixshop.model.Order;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long orderId = 0L;
    private static Long shoppingCartId = 0L;
    private static Long userId = 0L;

    public static void addOrder(Order order) {
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }

    public static void addUser(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartId++;
        shoppingCart.setId(shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }
}
