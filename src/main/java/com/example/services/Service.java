package com.example.services;

import com.example.db.entities.Asset;
import com.example.db.service.allAssetsService;
import com.example.db.util.convertToProtoClass;
import com.google.protobuf.Empty;
import com.server.protos.*;
import io.grpc.stub.StreamObserver;
import org.hibernate.SessionFactory;

import java.util.List;

public class Service extends CryptoGrpc.CryptoImplBase {
    private SessionFactory sessionFactory;
    private allAssetsService assetsService;

    public Service(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.assetsService = new allAssetsService(sessionFactory);
    }

    @Override
    public void getAsset(getAssetReq request, StreamObserver<getAssetResp> responseObserver) {
        Asset result = assetsService.findBySymbol(request.getSymbol());
        asset convertedResult = convertToProtoClass.convertToAsset(result);

        responseObserver.onNext(getAssetResp.newBuilder().setResponse(convertedResult).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getAllAsset(Empty request, StreamObserver<getAllAssetResp> responseObserver) {
        List<Asset> result = assetsService.findAll();

        List<asset> convertedResult = convertToProtoClass.convertMultipleAsset(result);
        responseObserver.onNext(getAllAssetResp.newBuilder().addAllResponse(convertedResult).build());

        responseObserver.onCompleted();
    }
}