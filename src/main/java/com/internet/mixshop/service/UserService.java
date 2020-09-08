package com.internet.mixshop.service;

import com.internet.mixshop.model.User;

public interface UserService extends GenericService<User, Long> {
    User update(User user);
}
