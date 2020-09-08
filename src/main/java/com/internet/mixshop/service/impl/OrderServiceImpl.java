package com.internet.mixshop.service.impl;

import com.internet.mixshop.dao.OrderDao;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.Order;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.service.OrderService;
import com.internet.mixshop.service.ShoppingCartService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    OrderDao orderDao;
    @Inject
    ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getUserId());
        order.setProducts(List.copyOf(shoppingCart.getProducts()));
        orderDao.create(order);
        shoppingCartService.clear(shoppingCart);
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getAllOrders()
                .stream()
                .filter(order -> order.getUserid().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long id) {
        return orderDao.getById(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAllOrders();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
