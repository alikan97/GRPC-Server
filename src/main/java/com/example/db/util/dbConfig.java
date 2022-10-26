package com.example.db.util;

import com.example.db.entities.AssetQuote;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import com.example.db.entities.allassets;
import com.example.db.entities.recenttrades;

public class dbConfig {
    private static SessionFactory sessionFactory = buildSessionFactory();

    protected dbConfig() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                String port = System.getProperty("POSTGRES_PORT");
                String host = System.getProperty("POSTGRES_HOST");
                String dbName = System.getProperty("POSTGRES_DB_NAME");
                String user = System.getProperty("POSTGRES_USER");
                String password = System.getProperty("POSTGRES_PASSWORD");

                Configuration cfg = new Configuration().configure()
                        .setProperty("connection.driver_class", "org.postgresql.Driver")
                        .setProperty("hibernate.connection.url", String.format("jdbc:postgresql://" + host + ":" + port + "/" + dbName))
                        .setProperty("hibernate.connection.username", user)
                        .setProperty("hibernate.connection.password", password)
                        .setProperty("connection.pool_size", "1")
                        .setProperty("show_sql", "true")
                        .setProperty("hbm2ddl.auto", "update");

                cfg.addAnnotatedClass(allassets.class);
                cfg.addAnnotatedClass(AssetQuote.class);
                cfg.addAnnotatedClass(recenttrades.class);

                StandardServiceRegistry srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();

                sessionFactory = cfg.buildSessionFactory(srb);
            }
            return sessionFactory;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
