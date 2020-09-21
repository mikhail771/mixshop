package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Role;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.ShoppingCartService;
import com.internet.mixshop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User grinch = new User("Mr.Grinch", "login", "1234");
        User morty = new User("Morty", "rick", "jessika");
        User admin = new User("admin", "admin", "1234");
        admin.setRoles(Set.of((Role.of("ADMIN"))));
        grinch.setRoles(Set.of((Role.of("USER"))));
        morty.setRoles(Set.of((Role.of("USER"))));
        userService.create(admin);
        userService.create(grinch);
        userService.create(morty);
        ShoppingCart cart = new ShoppingCart(grinch.getId());
        ShoppingCart cart2 = new ShoppingCart(morty.getId());
        shoppingCartService.create(cart);
        shoppingCartService.create(cart2);
        req.getRequestDispatcher("/WEB-INF/view/injectData.jsp").forward(req, resp);
    }
}
