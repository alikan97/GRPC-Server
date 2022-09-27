package com.example.db.entities;

import jakarta.persistence.*;

import java.time.OffsetTime;

@Entity(name = "AssetQuote")
public class AssetQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "SymbolName")
    private String SymbolName;
    @Column(name = "Price")
    private double Price;
    @Column(name = "RecordedAt")
    private OffsetTime RecordedAt;

    public AssetQuote () {}

    public AssetQuote(String symbolName, double price, OffsetTime recordedAt) {
        SymbolName = symbolName;
        Price = price;
        RecordedAt = recordedAt;
    }

    public String getSymbolName() {
        return SymbolName;
    }

    public void setSymbolName(String symbolName) {
        SymbolName = symbolName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public OffsetTime getRecordedAt() {
        return RecordedAt;
    }

    public void setRecordedAt(OffsetTime recordedAt) {
        RecordedAt = recordedAt;
    }

    @Override
    public String toString() {
        return "AssetQuote{" +
                "SymbolName='" + SymbolName + '\'' +
                ", Price=" + Price +
                ", RecordedAt=" + RecordedAt +
                '}';
    }
}
