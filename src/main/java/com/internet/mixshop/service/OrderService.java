package com.internet.mixshop.service;

import com.internet.mixshop.model.Order;
import com.internet.mixshop.model.ShoppingCart;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);
}
