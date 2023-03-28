package com.stockify.stockifyapp.models;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PorfolioMovement {

    UUID id;
    Stock stock;
    int quantity;
    double price;
    Date date;

    public PorfolioMovement(UUID id, Stock stock, int quantity, double price, Date date) {
        this.id = id;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }


    public PorfolioMovement(Stock stock, int quantity, double price, Date date) {
        this(UUID.randomUUID(), stock, quantity, price, date);
        
    }

    public UUID getId() {
        return id;
    }

    public HashMap<String, Object> getPortfolioMovementMap() {
        HashMap<String, Object> movementMap = new HashMap<>();
        movementMap.put("id", id);
        movementMap.put("stock", stock.getStockMap());
        movementMap.put("quantity", quantity);
        movementMap.put("price", price);
        movementMap.put("date", date);

        return movementMap;
    }
    

}
