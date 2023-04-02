package com.stockify.stockifyapp.restservices;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.commons.Checker;
import com.stockify.stockifyapp.commons.Converter;
import com.stockify.stockifyapp.models.PortfolioManager;
import com.stockify.stockifyapp.models.PorfolioMovement;
import com.stockify.stockifyapp.models.Stock;

@Service
public class PortfolioService {
    private PortfolioManager portfolioManager;

    public PortfolioService() {
        this.portfolioManager = new PortfolioManager();
    }

    public Optional<ArrayList<HashMap<String, Object>>> getPortfolioMap() {
        ArrayList<HashMap<String, Object>> map = portfolioManager.getPortfolioMap();
        return Optional.ofNullable(map.size() == 0 ? null : map);
    }


    public List<PorfolioMovement> getPortfolio() {
        return portfolioManager.getPortfolio();
    }

    

    
    public PorfolioMovement addPorfolioMovement(Map<String, Object> payload) throws Exception {
        try {
            checkIfPayloadIsValid(payload);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        Map<String, String> stock = (Map<String, String>) payload.get("stock");
        String ticker = stock.get("ticker");
        int quantity = (int) payload.get("quantity");
        // you have this value "2023-03-10"
        //convert it to a date


        Date date = Converter.convertObjectToDate(payload.get("date"));
        double price = Converter.convertObjectToDouble(payload.get("price"));

        PorfolioMovement movement = new PorfolioMovement(new Stock(ticker), quantity, price, date);
        portfolioManager.addMovement(movement);
        return movement;
    }


    public void checkIfPayloadIsValid(Map<String, Object> payload) throws ParseException {
        if (payload == null) {
            throw new IllegalArgumentException("Payload is null");
        }
        if (!payload.containsKey("stock")) {
            throw new IllegalArgumentException("Payload does not contain stock");
        }
        if (!payload.containsKey("quantity")) {
            throw new IllegalArgumentException("Payload does not contain quantity");
        }
        // check quantity is > 0
        if((int) payload.get("quantity") <= 0) {
            throw new IllegalArgumentException("Payload quantity is not > 0");
        }
        if (!payload.containsKey("price")) {
            throw new IllegalArgumentException("Payload does not contain price");
        }
        if (!payload.containsKey("date")) {
            throw new IllegalArgumentException("Payload does not contain date");
        }

        if (payload.get("stock") instanceof Map) {
            Map<String, String> stock = (Map<String, String>) payload.get("stock");
            if (!stock.containsKey("ticker")) {
                throw new IllegalArgumentException("Payload does not contain stock ticker");
            }
        } else {
            throw new IllegalArgumentException("Payload stock is not a map");
        }

        if (!Checker.isNumber(payload.get("quantity"))) {
            throw new IllegalArgumentException("Payload quantity is not a number");
        }

        if (!Checker.isNumber(payload.get("price"))) {
            throw new IllegalArgumentException("Payload price is not a number");
        }

        if (payload.get("date") instanceof Date) {
            throw new IllegalArgumentException("Payload date is not a date");
        }

        // verify date is in the past
        Date date = Converter.convertObjectToDate(payload.get("date"));
        if (date.after(new Date())) {
            throw new IllegalArgumentException("Payload date is in the future");
        }
        
    }

}
