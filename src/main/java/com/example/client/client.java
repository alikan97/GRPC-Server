package com.example.client;

import com.server.protos.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class client {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        CryptoGrpc.CryptoBlockingStub stub = CryptoGrpc.newBlockingStub(channel);

        getAssetReq reqq = getAssetReq.newBuilder().setSymbol("").build();

        getAssetResp d = stub.getAsset(reqq);

        System.out.println(d.toString());
    }
}
