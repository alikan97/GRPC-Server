package com.example.db.service;

import com.example.db.entities.AssetQuote;
import com.example.db.entities.allassets;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class AssetQuoteService extends genericDaoImpl<AssetQuote> {
    public AssetQuoteService(SessionFactory sesh) {
        super(sesh, (Class<AssetQuote>) new AssetQuote().getClass());
    }

    public void updateQuote(AssetQuote quoteToUpdate) {
        String queryString = "Update AssetQuote as A set A.Price=:newPrice, A.RecordedAt=:recordedTime where A.SymbolName=:symbolName";
        sessionFactory.getCurrentSession().beginTransaction();

        sessionFactory.getCurrentSession().createQuery(queryString)
                .setParameter("newPrice", quoteToUpdate.getPrice())
                .setParameter("recordedTime", quoteToUpdate.getRecordedAt())
                .setParameter("symbolName", quoteToUpdate.getSymbolName())
                .executeUpdate();

        sessionFactory.getCurrentSession().getTransaction().commit();
    }
}
