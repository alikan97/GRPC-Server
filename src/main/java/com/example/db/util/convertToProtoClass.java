package com.example.db.util;

import com.example.db.entities.allassets;
import com.server.protos.asset;

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
}
