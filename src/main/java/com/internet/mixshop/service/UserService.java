package com.internet.mixshop.service;

import com.internet.mixshop.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User update(User user);

    Optional<User> findByLogin(String login);

    User getUserWithHashPassword(User user);
}
