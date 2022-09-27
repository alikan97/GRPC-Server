package com.example.db.service;

import com.example.db.entities.allassets;
import com.example.db.entities.recenttrades;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class recentTradesService extends genericDaoImpl<recenttrades>{
    public recentTradesService(SessionFactory sesh) {
        super(sesh, (Class<recenttrades>) new recenttrades().getClass());
    }

    public List<recenttrades> findSince(Long since, String symbolName) {
        String rawQuery = "FROM recenttrades as R WHERE (R.symbolName = :symbolName AND R.tradeTime > :duration) ORDER BY R.symbolName ASC";
        sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery(rawQuery);
        query.setParameter("duration", since);
        query.setParameter("symbolName", symbolName);
        List<recenttrades> result = (List<recenttrades>) query.list();
        return result;
    }
}
