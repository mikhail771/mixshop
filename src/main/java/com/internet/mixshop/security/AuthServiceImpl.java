package com.internet.mixshop.security;

import com.internet.mixshop.exception.AuthenticationException;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;
import com.internet.mixshop.util.HashUtil;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByLogin(login);
        if (userFromDB.isPresent() && isPasswordValid(userFromDB.get(), password)) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    private boolean isPasswordValid(User userFromDB, String password) {

        return HashUtil.hashPassword(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword()
        );
    }
}
