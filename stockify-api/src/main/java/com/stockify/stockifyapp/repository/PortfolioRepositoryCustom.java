package com.stockify.stockifyapp.repository;

import java.util.List;

import com.stockify.stockifyapp.model.PortfolioMovement;

public interface PortfolioRepositoryCustom {
    void saveAllMovements(List<PortfolioMovement> movements);
}
