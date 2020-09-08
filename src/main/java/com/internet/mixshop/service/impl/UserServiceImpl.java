package com.internet.mixshop.service.impl;

import com.internet.mixshop.dao.UserDao;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.lib.Service;
import com.internet.mixshop.model.User;
import com.internet.mixshop.service.ShoppingCartService;
import com.internet.mixshop.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.getById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        ShoppingCartService shopCartService = new ShoppingCartServiceImpl();
        shopCartService.delete(shopCartService.getByUserId(id).get());
        return userDao.delete(id);
    }
}
