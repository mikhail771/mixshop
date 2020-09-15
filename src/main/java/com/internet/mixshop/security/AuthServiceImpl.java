package com.internet.mixshop.security;

import com.internet.mixshop.exception.AuthenticationException;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByLogin(login);
        if (userFromDB.isPresent() && userFromDB.get().getPassword().equals(password)) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
