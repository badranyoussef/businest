package org.daos;

import java.util.List;

public interface InterfaceDAO<T> {
    T getById(int id);
    T create(T entity);
    int update(T entity);
    int delete(int id);
}
