package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.service.ProductService;
import com.internet.mixshop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteProductFromCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        ShoppingCart shoppingCart =
                shoppingCartService.getByUserId(userId);
        List<Product> products = shoppingCart.getProducts();
        String productId = req.getParameter("id");
        Product deletedProduct = productService.getById(Long.valueOf(productId));
        shoppingCartService.deleteProduct(shoppingCart, deletedProduct);
        double sum = shoppingCartService.findTotalPrice(shoppingCart);
        req.setAttribute("products", products);
        req.setAttribute("sum", sum);
        resp.sendRedirect(req.getContextPath() + "/cart/products");
    }
}
