package com.stockify.stockifyapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class SuscriptionPlanServiceInitialData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void addBulkData() {
        jdbcTemplate.execute(
                "INSERT INTO suscription_plan (id, name, price, description) VALUES (1, 'Basic', 0, 'Basic plan')");
        jdbcTemplate.execute(
                "INSERT INTO suscription_plan (id, name, price, description) VALUES (2, 'Premium', 10, 'Premium plan')");
        jdbcTemplate.execute(
                "INSERT INTO suscription_plan (id, name, price, description) VALUES (3, 'Gold', 20, 'Gold plan')");
    }

}
