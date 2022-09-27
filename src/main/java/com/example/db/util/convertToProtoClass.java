package com.example.db.util;

import com.example.db.entities.AssetQuote;
import com.example.db.entities.allassets;
import com.example.db.entities.recenttrades;
import com.server.protos.*;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class convertToProtoClass {
    public static asset convertToAsset(allassets rawClass) {
        asset converted = asset.newBuilder().setName(
                rawClass.getAssetName()).setSymbol(rawClass.getAssetCode())
                .build();

        return converted;
    }

    public static List<asset> convertMultipleAsset (List<allassets> rawClass) {
        List<asset> converted = new ArrayList<>();

        for(allassets var : rawClass) {
            converted.add(convertToAsset(var));
        }

        return converted;
    }

    public static recentTradesMultiple convertToRecentTrades(List<recenttrades> raw){
        recentTradesMultiple.Builder result = recentTradesMultiple.newBuilder();

        for(recenttrades var : raw) {
            recentTradesResp singleClass = recentTradesResp.newBuilder()
                    .setSymbolName(var.getSymbolName())
                    .setQuantity(var.getQuantity())
                    .setTime(var.getTradeTime())
                    .build();

            result.addListResponse(singleClass);
        }

        return result.build();
    }

    public static recenttrades convertToDbRecentTrade (addRecentTradeReq request) {
        recenttrades entity = new recenttrades();
        entity.setTradeTime(request.getTime());
        entity.setQuantity(request.getQuantity());
        entity.setSymbolName(request.getAssetName());
        entity.setPrice(request.getPrice());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

     public static AssetQuote convertToAssetQuote (updateQuoteReq request) {
        String symbol = request.getSymbol();
        Double price = request.getUpdatedPrice();
        OffsetTime now = OffsetTime.now();

        return new AssetQuote(symbol, price, now);
     }
}
