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
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.time.OffsetTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        if (request.getSymbol().isEmpty()) {
            throw new IllegalArgumentException("Incorrect arguments provided");
        }

        allassets result = assetsService.findBySymbol(request.getSymbol());

        if (result.getAssetCode().isEmpty()) {
            String errorResponse = String.format("Could not find asset %s", request.getSymbol());
            throw new EntityNotFoundException(errorResponse);
        }

        asset convertedResult = convertToProtoClass.convertToAsset(result);
        responseObserver.onNext(getAssetResp.newBuilder().setResponse(convertedResult).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getAllAsset(Empty request, StreamObserver<getAllAssetResp> responseObserver) {
        List<allassets> result = assetsService.findAll();

        if (result.isEmpty()) {
            String errorResponse = String.format("Could not retrieve assets");
            throw new EntityNotFoundException(errorResponse);
        }

        List<asset> convertedResult = convertToProtoClass.convertMultipleAsset(result);

        responseObserver.onNext(getAllAssetResp.newBuilder().addAllResponse(convertedResult).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getRecentTrades(recentTradesReq request, StreamObserver<recentTradesMultiple> responseObserver) {
        if (request.getSymbol().isEmpty() || request.getSince() > Instant.now().toEpochMilli()) {
            throw new IllegalArgumentException("Incorrect arguments provided");
        }

        List<recenttrades> results = recentService.findSince(request.getSince(),request.getSymbol());

        if (results.isEmpty()) {
            String errorResponse = String.format("Could not retrieve recent trades for asset %s from %s",
                    request.getSymbol(),
                    new Date(request.getSince()).toString());

            throw new EntityNotFoundException(errorResponse);
        }

        recentTradesMultiple convertedResults = convertToProtoClass.convertToRecentTrades(results);
        responseObserver.onNext(convertedResults);

        responseObserver.onCompleted();
    }

    @Override
    public void addRecentTrade(addRecentTradeReqMulti request, StreamObserver<Empty> responseObserver) {
        if (request.getRecentTradeList().size() <= 0) {
            throw new IllegalArgumentException("Must provide data");
        }
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
        if (request.getSymbol().isEmpty() || request.getUpdatedPrice() < 0) {
            throw new IllegalArgumentException("Invalid data provided");
        }
        AssetQuote quoteToUpdate = convertToProtoClass.convertToAssetQuote(request);

        assetQuoteService.updateQuote(quoteToUpdate);
        responseObserver.onNext(Empty.newBuilder().build());

        responseObserver.onCompleted();
    }

    @Override
    public void getCurrentQuote(getQuoteReq request, StreamObserver<quoteResp> responseObserver) {
        if (request.getSymbol().isEmpty()) {
            throw new IllegalArgumentException("Invalid data provided");
        }
        List<AssetQuote> quote = assetQuoteService.findAll();
        AssetQuote finalQuote = quote.stream().filter(assetQuote ->
                request.getSymbol().equals(assetQuote.getSymbolName()))
                .findFirst().orElse(null);
        if (finalQuote.getSymbolName().isEmpty()) {
            String errorResponse = String.format("Could not retrieve quote for asset %s",
                    request.getSymbol());

            throw new EntityNotFoundException(errorResponse);
        }

        quoteResp response = quoteResp.newBuilder()
                .setPrice(finalQuote.getPrice())
                .setSymbol(finalQuote.getSymbolName())
                .setLastUpdated(Timestamp.newBuilder().setSeconds(finalQuote.getRecordedAt().getSecond()))
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }
}