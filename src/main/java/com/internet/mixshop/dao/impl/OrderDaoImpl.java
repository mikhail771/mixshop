package com.internet.mixshop.dao.impl;

import com.internet.mixshop.dao.OrderDao;
import com.internet.mixshop.db.Storage;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> getById(Long orderId) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(i -> Storage.orders.get(i).getId().equals(order.getId()))
                .forEach(i -> Storage.orders.set(i, order));
        return order;
    }

    @Override
    public boolean delete(Long orderId) {
        return Storage.orders.removeIf(order -> order.getId().equals(orderId));
    }

    @Override
    public List<Order> getAllOrders() {
        return Storage.orders;
    }
}
