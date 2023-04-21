package com.stockify.stockifyapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("SUBSCRIPTION_PLANS")
public class SubscriptionPlan {

    @Id
    private int id;
    private String name;
    private double price;

    // Add an empty constructor for JPA
    public SubscriptionPlan() {
    }

    public SubscriptionPlan(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
