package com.example.db.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "recenttrades")
public class recenttrades {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "symbolname")
    private String symbolName;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "tradetime")
    private Long tradeTime;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public recenttrades() {}

    public recenttrades(String symbolName, double price, double quantity, Long tradeTime, LocalDateTime updatedAt) {
        this.symbolName = symbolName;
        this.price = price;
        this.quantity = quantity;
        this.tradeTime = tradeTime;
        this.updatedAt = updatedAt;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Long tradeTime) {
        this.tradeTime = tradeTime;
    }

}
