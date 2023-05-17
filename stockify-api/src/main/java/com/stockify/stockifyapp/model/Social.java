package com.stockify.stockifyapp.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SOCIAL")
public class Social {

    @Column("USER_NAME")
    String name;

    @Column("USER_EMAIL")
    String email;

    @Column("SUBSCRIPTION_PLAN")
    String subscription_plan;

    @Column("PORTFOLIO_MOVEMENTS")
    Integer portfolio_movements;

    public Social() {
    }

    public Social(String name, String email, String suscription_plan, Integer portfolio_movements) {
        this.name = name;
        this.email = email;
        this.subscription_plan = suscription_plan;
        this.portfolio_movements = portfolio_movements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscription_plan() {
        return subscription_plan;
    }

    public void setSubscription_plan(String suscription_plan) {
        this.subscription_plan = suscription_plan;
    }

    public Integer getPortfolio_movements() {
        return portfolio_movements;
    }

    public void setPortfolio_movements(Integer portfolio_movements) {
        this.portfolio_movements = portfolio_movements;
    }




    
    
}
