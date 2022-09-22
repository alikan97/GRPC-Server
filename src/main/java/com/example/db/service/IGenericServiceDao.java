package com.example.db.service;

import java.util.List;

public interface IGenericServiceDao<T> {
    public T findOne(long id);
    public List<T> findAll();
    public T create (T entity);
    public int update (T entity);
    public void delete (T entity);
    public void deleteById(long entityId);
}
