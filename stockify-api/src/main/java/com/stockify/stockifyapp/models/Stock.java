package com.stockify.stockifyapp.models;

import java.util.HashMap;

public class Stock {
    String ticker;

    public Stock(String ticker) {
        this.ticker = ticker;
    }

    public HashMap<String, Object> getStockMap() {
        HashMap<String, Object> stockMap = new HashMap<>();
        stockMap.put("ticker", ticker);
        return stockMap;
    }
}
