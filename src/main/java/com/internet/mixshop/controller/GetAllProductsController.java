package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAllProductsController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productService.getAll();
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        req.setAttribute("products", products);
        req.getRequestDispatcher("WEB-INF/view/product/all-products.jsp").forward(req, resp);
    }
}
