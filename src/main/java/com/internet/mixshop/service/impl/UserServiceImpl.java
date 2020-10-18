package com.internet.mixshop.service.impl;

import com.internet.mixshop.dao.UserDao;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.UserService;
import com.internet.mixshop.util.HashUtil;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(getUserWithHashPassword(user));
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public User getUserWithHashPassword(User user) {
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setSalt(salt);
        return user;
    }
}
