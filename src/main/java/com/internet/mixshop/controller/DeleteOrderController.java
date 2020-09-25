package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        orderService.delete(Long.valueOf(id));
        resp.sendRedirect(req.getContextPath() + "/orders");
    }
}
