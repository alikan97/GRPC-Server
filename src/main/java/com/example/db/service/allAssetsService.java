package com.example.db.service;

import com.example.db.entities.Asset;
import org.hibernate.SessionFactory;
import org.hibernate.query.*;

public class allAssetsService extends genericDaoImpl<Asset> {
    public allAssetsService(SessionFactory sessionFactory) {
        super(sessionFactory, (Class<Asset>) new Asset().getClass());
    }

    public Asset findBySymbol (String symbol) {
        sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("from allassets a where a.assetCode=:symbol");
        Asset result = (Asset) query.setParameter("symbol", symbol).getSingleResult();

        sessionFactory.getCurrentSession().getTransaction().commit();
        return result;
    }
}
