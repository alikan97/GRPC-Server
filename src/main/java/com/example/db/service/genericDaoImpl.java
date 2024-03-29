package com.example.db.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.concurrent.Callable;

public class genericDaoImpl<T> implements IGenericServiceDao<T> {
    private Class<T> table;
    protected SessionFactory sessionFactory;

    public genericDaoImpl(SessionFactory sesh, Class<T> tClass) {
        this.sessionFactory = sesh;
        this.table = tClass;
    }

    public T findOne(long id) {
        return (T) sessionFactory.getCurrentSession().get(table, id);
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public List<T> findAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            currentSession.beginTransaction();
            return currentSession.createQuery(String.format("from %s", table.getSimpleName())).list();
        } catch (Exception e) {
            System.out.printf("Error %s", e.toString());
            return null;
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }
    @SuppressWarnings("deprecation")
    public T create(T entity) {
        if (entity == null) {
            System.out.println("Error");
            return null;
        }
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().save(entity);
            return entity;
        } catch (Exception e) {
            System.out.printf("Error: %s", e.toString());
            return null;
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }

    public int update (T entity) {
        if (entity != null) {
            sessionFactory.getCurrentSession().merge(entity);
        }
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().merge(entity);
            return 1;
        } catch (Exception e) {
            System.out.printf("Error: %s", e.toString());
            return -1;
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }
    @SuppressWarnings("deprecation")
    public void delete (T entity) {
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().delete(entity);
        } catch (Exception e) {
            System.out.printf("Error: %s", e.toString());
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }

    public void deleteById(long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }

    private T safeExecuteQuery(Callable<T> func) throws Exception {
        sessionFactory.getCurrentSession();
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            T result = (T) func.call();
            return result;
        } catch (Exception e) {
            throw new Exception("DB Query failed");
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
            return null;
        }
    }
}
