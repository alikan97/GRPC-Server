package com.example.db.service;

import com.example.db.entities.allassets;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

public class allAssetsService extends genericDaoImpl<allassets> {
    public allAssetsService(SessionFactory sessionFactory) {
        super(sessionFactory, (Class<allassets>) new allassets().getClass());
    }

    public allassets findBySymbol (String symbol) {
        sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("from allassets a where a.assetCode=:symbol");
        allassets result = (allassets) query.setParameter("symbol", symbol).getSingleResult();

        sessionFactory.getCurrentSession().getTransaction().commit();
        return result;
    }
}
