package com.internet.mixshop.security;

import com.internet.mixshop.exception.AuthenticationException;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDB = userService.findByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Incorrect login or password"));
        if (userFromDB.getPassword().equals(password)) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
