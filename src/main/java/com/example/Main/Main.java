package com.example.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.example.db.entities.Asset;
import com.example.db.service.allAssetsService;
import com.example.db.util.dbConfig;
//import com.example.services.Service;
import com.example.services.Service;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/** main class to start grpc server on port 8080 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SessionFactory sessionFactory = dbConfig.getSessionFactory();

       // Create a new server to listen on port 8080
        Server server = ServerBuilder.forPort(8080).addService(new Service(sessionFactory)).build();

        server.start();

        // Don't exit the main thread. Wait until server is terminated.
        server.awaitTermination();
    }
}