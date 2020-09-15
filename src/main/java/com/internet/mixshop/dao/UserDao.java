package com.internet.mixshop.dao;

import com.internet.mixshop.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);
}
