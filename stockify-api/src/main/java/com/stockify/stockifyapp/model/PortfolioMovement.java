package com.stockify.stockifyapp.model;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("PORTFOLIO_MOVEMENTS")
public class PortfolioMovement {

    @Id
    private Integer id;
    @Column("TICKER")
    private String ticker;
    @Column("QUANTITY")
    private Integer quantity;
    @Column("PRICE")
    private double price;
    @Column("DATE")
    private LocalDate date;
    @Column("USER_ID")
    private Integer userId;


    public PortfolioMovement() {
    }



    public PortfolioMovement(Integer id, String ticker, int quantity, double price, LocalDate date, Integer userId) {
        this.id = id;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.userId = userId;
    }



    public PortfolioMovement(String ticker, int quantity, double price, LocalDate date, Integer userId) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.userId = userId;
    }

  
    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getTicker() {
        return ticker;
    }



    public void setTicker(String ticker) {
        this.ticker = ticker;
    }



    public int getQuantity() {
        return quantity;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public double getPrice() {
        return price;
    }



    public void setPrice(double price) {
        this.price = price;
    }



    public LocalDate getDate() {
        return date;
    }



    public void setDate(LocalDate date) {
        this.date = date;
    }



    public Integer getUserId() {
        return userId;
    }



    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    

}
