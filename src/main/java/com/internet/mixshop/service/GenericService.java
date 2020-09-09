package com.internet.mixshop.service;

import java.util.List;

public interface GenericService<T, K> {
    T create(T item);

    T getById(K id);

    List<T> getAll();

    boolean delete(K id);
}
