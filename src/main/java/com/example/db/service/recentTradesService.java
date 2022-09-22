package com.example.db.service;

import com.example.db.entities.recenttrades;
import org.hibernate.SessionFactory;

public class recentTradesService extends genericDaoImpl<recenttrades>{
    public recentTradesService(SessionFactory sesh) {
        super(sesh, (Class<recenttrades>) new recenttrades().getClass());
    }
}
