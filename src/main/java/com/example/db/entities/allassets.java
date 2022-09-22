package com.example.db.entities;

import jakarta.persistence.*;

@Entity(name = "allassets")
public class allassets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "assetname")
    private String assetName;
    @Column(name = "assetcode")
    private String assetCode;

    public String getAssetName() {
        return assetName;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public allassets(String assetName, String assetCode) {
        this.assetName = assetName;
        this.assetCode = assetCode;
    }

    public allassets() {
    }
    @Override
    public String toString() {
        return String.format("%s %s",assetCode, assetName);
    }
}