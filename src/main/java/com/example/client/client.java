package com.example.client;

import com.server.protos.CryptoGrpc;
import com.server.protos.getAssetReq;
import com.server.protos.getAssetResp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Empty;

public class client {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        CryptoGrpc.CryptoBlockingStub stub = CryptoGrpc.newBlockingStub(channel);

        getAssetResp ed = stub.getAsset(getAssetReq.newBuilder().setSymbol("ETH").build());
        System.out.println(ed);
    }
}
