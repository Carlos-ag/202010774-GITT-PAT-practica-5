package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.stockify.stockifyapp.model.SubscriptionPlan;
import com.stockify.stockifyapp.service.SuscriptionPlanService;

@RestController
public class SuscriptionPlanController {

    private SuscriptionPlanService suscriptionPlanService;

    public SuscriptionPlanController(SuscriptionPlanService suscriptionPlanService) {
        this.suscriptionPlanService = suscriptionPlanService;
    }

    @GetMapping("/suscriptionPlans/{suscriptionPlanID}")
    @CrossOrigin(origins = "*")
    public SubscriptionPlan getSuscriptionPlanInfo(@PathVariable("suscriptionPlanID") Integer suscriptionPlanID) {
        return suscriptionPlanService.getSuscriptionPlanInfo(suscriptionPlanID);
    }

    @GetMapping("/subscriptionPlans")
    @CrossOrigin(origins = "*")
    public Iterable<SubscriptionPlan> getAllSuscriptionPlans() {
        return suscriptionPlanService.getAllSuscriptionPlans();
    }

    @ExceptionHandler(IllegalArgumentException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public String handleIllegalArgumentException(IllegalArgumentException exception) {
    return exception.getMessage();
}

@ExceptionHandler(RuntimeException.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public String handleRuntimeException(RuntimeException exception) {
    return exception.getMessage();
}


    
}
