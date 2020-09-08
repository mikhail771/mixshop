package com.internet.mixshop.dao;

import com.internet.mixshop.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    Optional<Order> getById(Long orderId);

    Order update(Order order);

    boolean delete(Long orderId);

    List<Order> getAllOrders();
}
