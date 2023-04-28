package com.stockify.stockifyapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockify.stockifyapp.model.PortfolioMovement;

@Repository
public interface PortfolioRepository extends CrudRepository<PortfolioMovement, Integer>, PortfolioRepositoryCustom{

    List<PortfolioMovement> findByUserId(Integer userId);
    void deleteById(Integer id);
    
}
