package org.thiago.moyses.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.thiago.moyses.util.JPAUtil;

import java.util.List;
import java.util.function.Consumer;

public class GenericDao <T>{

    private final Class<T> tclass;

    public GenericDao(Class<T> tclass){
        this.tclass = tclass;
    }

    public void save(T entity) {
        executeInsideTransaction(session -> session.persist(entity));
    }

    public void update(T entity) {
        executeInsideTransaction(session -> session.merge(entity));
    }

    public void delete(T entity) {
        executeInsideTransaction(session -> session.remove(entity));
    }

    public T findById(Long id) {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            return session.get(tclass, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + tclass.getName()).list();
        }
    }

    // Function create to avoid code duplication
    private void executeInsideTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}