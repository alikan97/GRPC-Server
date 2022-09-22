package com.example.db.util;

import com.example.db.entities.Asset;
import com.server.protos.asset;

import java.util.ArrayList;
import java.util.List;

public class convertToProtoClass {
    public static asset convertToAsset(Asset rawClass) {
        asset converted = asset.newBuilder().setName(
                rawClass.getAssetName()).setSymbol(rawClass.getAssetCode())
                .build();

        return converted;
    }

    public static List<asset> convertMultipleAsset (List<Asset> rawClass) {
        List<asset> converted = new ArrayList<>();

        for(Asset var : rawClass) {
            converted.add(convertToAsset(var));
        }

        return converted;
    }
}
