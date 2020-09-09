package com.internet.mixshop.controller;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> allUsers = userService.getAll();
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("WEB-INF/view/user/all.jsp").forward(req, resp);
    }
}
