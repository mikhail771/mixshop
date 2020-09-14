package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.service.OrderService;
import com.internet.mixshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrderController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long cartId = Long.parseLong(req.getParameter("id"));
        orderService.completeOrder(shoppingCartService.getById(cartId));
        resp.sendRedirect("/products");
    }
}
