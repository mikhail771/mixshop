package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Order;
import com.internet.mixshop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllUserOrdersController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> userOrders = orderService.getUserOrders(USER_ID);
        req.setAttribute("orders", userOrders);
        req.getRequestDispatcher("WEB-INF/view/order/user-orders.jsp").forward(req, resp);
    }
}
