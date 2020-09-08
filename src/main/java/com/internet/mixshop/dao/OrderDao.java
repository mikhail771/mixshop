package com.internet.mixshop.dao;

import com.internet.mixshop.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(Long userId);
}
