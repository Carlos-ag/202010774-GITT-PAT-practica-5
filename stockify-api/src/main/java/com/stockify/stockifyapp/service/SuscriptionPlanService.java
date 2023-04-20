package com.stockify.stockifyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.SuscriptionPlan;
import com.stockify.stockifyapp.repository.SuscriptionPlanRepository;

@Service
public class SuscriptionPlanService {

    @Autowired
    private SuscriptionPlanRepository suscriptionPlanRepository;

    public SuscriptionPlan getSuscriptionPlanInfo(Integer suscriptionPlanID) {
        return suscriptionPlanRepository.findById(suscriptionPlanID).get();
    }
    
}
