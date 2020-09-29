package com.internet.mixshop.web.filters;

import com.internet.mixshop.lib.Injector;
import com.internet.mixshop.model.Role;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private static final Injector injector = Injector.getInstance("com.internet.mixshop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private Map<String, Set<String>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users", Set.of(ADMIN));
        protectedUrls.put("/products/add", Set.of(ADMIN));
        protectedUrls.put("/admin-orders", Set.of(ADMIN));
        protectedUrls.put("/admin-panel", Set.of(ADMIN));
        protectedUrls.put("/products/manage", Set.of(ADMIN));
        protectedUrls.put("/orders", Set.of(ADMIN));
        protectedUrls.put("/order/details", Set.of(ADMIN));
        protectedUrls.put("/cart/complete-order", Set.of(USER));
        protectedUrls.put("/cart/products/add", Set.of(USER));
        protectedUrls.put("/cart/products", Set.of(USER));
        protectedUrls.put("/cart/products/delete", Set.of(USER));
        protectedUrls.put("/user/orders", Set.of(USER));
        protectedUrls.put("/order/details", Set.of(USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();
        if (protectedUrls.get(requestedUrl) == null) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.getById(userId);
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, Set<String> authorizedRoles) {
        for (String authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
