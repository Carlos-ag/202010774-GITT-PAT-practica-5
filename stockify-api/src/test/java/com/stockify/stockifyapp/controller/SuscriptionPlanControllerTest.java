package com.stockify.stockifyapp.controller;

import com.stockify.stockifyapp.model.SubscriptionPlan;
import com.stockify.stockifyapp.service.SuscriptionPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SuscriptionPlanController.class)
public class SuscriptionPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuscriptionPlanService suscriptionPlanService;

    private SubscriptionPlan plan1;
    private SubscriptionPlan plan2;

    @BeforeEach
    public void setUp() {
        plan1 = new SubscriptionPlan("Basic", 9.99);
        plan1.setId(1);

        plan2 = new SubscriptionPlan("Premium", 19.99);
        plan2.setId(2);
    }

    @Test
    public void getSuscriptionPlanInfo_shouldReturnSuscriptionPlanInfo() throws Exception {
        given(suscriptionPlanService.getSuscriptionPlanInfo(1)).willReturn(plan1);

        mockMvc.perform(get("/suscriptionPlans/{suscriptionPlanID}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Basic"))
                .andExpect(jsonPath("price").value(9.99));
    }

    @Test
    public void getAllSuscriptionPlans_shouldReturnAllSuscriptionPlans() throws Exception {
        List<SubscriptionPlan> plans = Arrays.asList(plan1, plan2);
        given(suscriptionPlanService.getAllSuscriptionPlans()).willReturn(plans);

        mockMvc.perform(get("/subscriptionPlans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Basic"))
                .andExpect(jsonPath("$[0].price").value(9.99))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Premium"))
                .andExpect(jsonPath("$[1].price").value(19.99));
    }

    @Test
    public void getSuscriptionPlanInfo_shouldReturnBadRequest_whenInvalidId() throws Exception {
        given(suscriptionPlanService.getSuscriptionPlanInfo(anyInt())).willThrow(new IllegalArgumentException("Invalid subscription plan ID"));

        mockMvc.perform(get("/suscriptionPlans/{suscriptionPlanID}", -1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getSuscriptionPlanInfo_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        given(suscriptionPlanService.getSuscriptionPlanInfo(anyInt())).willThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/suscriptionPlans/{suscriptionPlanID}", 1))
                .andExpect(status().isInternalServerError());
    }
}
