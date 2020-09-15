package com.internet.mixshop.security;

import com.internet.mixshop.exception.AuthenticationException;
import com.internet.mixshop.model.User;

public interface AuthService {
    User login(String login, String password) throws AuthenticationException;
}
