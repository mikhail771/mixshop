package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Order;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsOrderController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Order order = orderService.getById(Long.valueOf(id));
        List<Product> products = order.getProducts();
        req.setAttribute("order", order);
        req.setAttribute("orderProducts", products);
        req.getRequestDispatcher("/WEB-INF/view/order/order-details.jsp").forward(req, resp);
    }
}
