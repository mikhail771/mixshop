package com.internet.mixshop.dao;

import com.internet.mixshop.model.Role;
import java.util.Set;

public interface RoleDao extends GenericDao<Role, Long> {
    Set<Role> getRolesByUserId(Long userId);
}
