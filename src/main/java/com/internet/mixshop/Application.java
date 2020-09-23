package com.internet.mixshop;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.ShoppingCart;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.OrderService;
import com.internet.mixshop.service.ShoppingCartService;
import com.internet.mixshop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.mixshop");

    public static void main(String[] args) {
        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = new User("Test user", "testtest", "password");
        User user2 = new User("Second test user", "testtest", "password123");
        userService.create(user);
        userService.create(user2);
        user2.setLogin("test222");
        System.out.println("New user");
        System.out.println(user);
        System.out.println("All users");
        userService.getAll().forEach(System.out::println);
        ShoppingCart cart = new ShoppingCart(user.getId());

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.create(cart);
        System.out.println(cart);
        System.out.println(shoppingCartService.getByUserId(user.getId()));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        System.out.println("After order:");
        orderService.completeOrder(cart);
        System.out.println(orderService.getUserOrders(user.getId()));
        System.out.println(cart);
    }
}
