package org.thiago.moyses.service;

import org.thiago.moyses.dao.GenericDao;

import java.util.List;

public class GenericService<T> {
    protected GenericDao<T> dao;

    public GenericService(Class<T> entityClass) {
        this.dao = new GenericDao<>(entityClass);
    }

    public void save(T entity) {
        dao.save(entity);
    }

    public void update(T entity) {
        dao.update(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    public T findById(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }
}