package com.internet.mixshop.service.impl;

import com.internet.mixshop.dao.OrderDao;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.Order;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.service.OrderService;
import com.internet.mixshop.service.ShoppingCartService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

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
        return orderDao.getUserOrders(userId);
    }

    @Override
    public Order create(Order item) {
        return orderDao.create(item);
    }

    @Override
    public Order getById(Long id) {
        return orderDao.getById(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
