package com.internet.mixshop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item);

    Optional<T> getById(K itemId);

    T update(T item);

    boolean delete(K itemId);

    List<T> getAll();
}
