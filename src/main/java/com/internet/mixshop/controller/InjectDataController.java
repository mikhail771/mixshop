package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.ProductService;
import com.internet.mixshop.service.ShoppingCartService;
import com.internet.mixshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User grinch = new User("Mr.Grinch", "login", "1234");
        User morty = new User("Morty", "rick", "jessika");
        userService.create(grinch);
        userService.create(morty);
        ShoppingCart cart = new ShoppingCart(grinch.getId());
        shoppingCartService.create(cart);
        Product milk = new Product("Burenka", 30.0);
        Product cookie = new Product("Yococo", 50.0);
        Product dreamDrink = new Product("dreamDrink", 40.0);
        productService.create(milk);
        productService.create(cookie);
        productService.create(dreamDrink);
        req.getRequestDispatcher("/WEB-INF/view/injectData.jsp").forward(req, resp);
    }
}
