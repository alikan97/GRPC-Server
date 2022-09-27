package com.example.client;

import com.server.protos.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.List;

public class client {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        CryptoGrpc.CryptoBlockingStub stub = CryptoGrpc.newBlockingStub(channel);
        updateQuoteReq reqq = updateQuoteReq.newBuilder().setSymbol("BTCUSDT").setUpdatedPrice(999999).build();
        stub.updateQuotes(reqq);
    }
}
