package com.internet.mixshop;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.mixshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product milk = new Product("Burenka", 30.0);
        Product cookie = new Product("Yococo", 50.0);
        Product dreamDrink = new Product("dreamDrink", 40.0);

        productService.create(milk);
        productService.create(cookie);
        productService.create(dreamDrink);
        System.out.println("Begin");
        productService.getAllProducts().forEach(System.out::println);

        milk.setPrice(29.99);
        productService.update(milk);
        System.out.println("After update");
        productService.getAllProducts().forEach(System.out::println);

        System.out.println("After delete");
        productService.deleteById(cookie.getId());
        productService.getAllProducts().forEach(System.out::println);
    }
}
