package com.stockify.stockifyapp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.stockify.stockifyapp.model.SubscriptionPlan;

@Repository
public interface SuscriptionPlanRepository extends CrudRepository<SubscriptionPlan, Integer> {
    
}
