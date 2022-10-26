package com.example.db.service;

import com.example.db.entities.allassets;
import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

public class allAssetsService extends genericDaoImpl<allassets> {
    public allAssetsService(SessionFactory sessionFactory) {
        super(sessionFactory, (Class<allassets>) new allassets().getClass());
    }

    public allassets findBySymbol (String symbol) {
        try {
            sessionFactory.getCurrentSession().beginTransaction();

            Query query = sessionFactory.getCurrentSession().createQuery("from allassets a where a.assetCode=:symbol");
            allassets result = (allassets) query.setParameter("symbol", symbol).getSingleResult();

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }
}
