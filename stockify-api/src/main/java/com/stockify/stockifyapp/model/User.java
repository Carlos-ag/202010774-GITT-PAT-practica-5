package com.stockify.stockifyapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public class User {
    @Id
    private Integer id;
    @Column("NAME")
    private String name;
    @Column("PHONE")
    private String phone;
    @Column("EMAIL")
    private String email;
    @Column("SUBSCRIPTION_PLAN")
    private AggregateReference<SubscriptionPlan, Integer> subscriptionPlan;


    public User() {
    }

    public User(String name, String phone, String email, AggregateReference<SubscriptionPlan, Integer> subscriptionPlan) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.subscriptionPlan = subscriptionPlan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AggregateReference<SubscriptionPlan, Integer> getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(AggregateReference<SubscriptionPlan, Integer> subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    
}
