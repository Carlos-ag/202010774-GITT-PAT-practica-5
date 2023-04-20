package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.model.SuscriptionPlan;
import com.stockify.stockifyapp.service.SuscriptionPlanService;

@RestController
public class SuscriptionPlanController {

    private SuscriptionPlanService suscriptionPlanService;

    public SuscriptionPlanController(SuscriptionPlanService suscriptionPlanService) {
        this.suscriptionPlanService = suscriptionPlanService;
    }

    @GetMapping("/suscriptionPlans/{suscriptionPlanID}")
    public SuscriptionPlan getSuscriptionPlanInfo(@PathVariable("suscriptionPlanID") Integer suscriptionPlanID) {
        return suscriptionPlanService.getSuscriptionPlanInfo(suscriptionPlanID);
    }
    
}
