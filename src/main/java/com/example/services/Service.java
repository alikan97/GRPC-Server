package com.example.services;

import com.example.db.entities.AssetQuote;
import com.example.db.entities.allassets;
import com.example.db.entities.recenttrades;
import com.example.db.service.AssetQuoteService;
import com.example.db.service.allAssetsService;
import com.example.db.service.recentTradesService;
import com.example.db.util.convertToProtoClass;
import com.google.protobuf.Empty;
import com.server.protos.*;
import io.grpc.stub.StreamObserver;
import org.hibernate.SessionFactory;

import java.util.List;

public class Service extends CryptoGrpc.CryptoImplBase {
    private allAssetsService assetsService;
    private recentTradesService recentService;
    private AssetQuoteService assetQuoteService;

    public Service(SessionFactory sessionFactory) {
        this.assetsService = new allAssetsService(sessionFactory);
        this.recentService = new recentTradesService(sessionFactory);
        this.assetQuoteService = new AssetQuoteService(sessionFactory);
    }

    @Override
    public void getAsset(getAssetReq request, StreamObserver<getAssetResp> responseObserver) {
        allassets result = assetsService.findBySymbol(request.getSymbol());
        asset convertedResult = convertToProtoClass.convertToAsset(result);

        responseObserver.onNext(getAssetResp.newBuilder().setResponse(convertedResult).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getAllAsset(Empty request, StreamObserver<getAllAssetResp> responseObserver) {
        List<allassets> result = assetsService.findAll();

        List<asset> convertedResult = convertToProtoClass.convertMultipleAsset(result);
        responseObserver.onNext(getAllAssetResp.newBuilder().addAllResponse(convertedResult).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getRecentTrades(recentTradesReq request, StreamObserver<recentTradesMultiple> responseObserver) {
        List<recenttrades> results = recentService.findSince(request.getSince(),request.getSymbol());
        recentTradesMultiple convertedResults = convertToProtoClass.convertToRecentTrades(results);

        responseObserver.onNext(convertedResults);

        responseObserver.onCompleted();
    }

    @Override
    public void addRecentTrade(addRecentTradeReqMulti request, StreamObserver<Empty> responseObserver) {
        List<addRecentTradeReq> req = request.getRecentTradeList();
        for (addRecentTradeReq var: req) {
            recenttrades entity = convertToProtoClass.convertToDbRecentTrade(var);
            recentService.create(entity);
        }

        responseObserver.onNext(Empty.newBuilder().build());

        responseObserver.onCompleted();
    }

    @Override
    public void updateQuotes(updateQuoteReq request, StreamObserver<Empty> responseObserver) {
        AssetQuote quoteToUpdate = convertToProtoClass.convertToAssetQuote(request);

        assetQuoteService.updateQuote(quoteToUpdate);

        responseObserver.onNext(Empty.newBuilder().build());

        responseObserver.onCompleted();
    }
}