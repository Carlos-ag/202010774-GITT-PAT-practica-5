package com.stockify.stockifyapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.stockify.stockifyapp.model.PortfolioMovement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PortfolioRepositoryImpl implements PortfolioRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void saveAllMovements(List<PortfolioMovement> movements) {
        String sql = "INSERT INTO PORTFOLIO_MOVEMENTS (USER_ID, TICKER, QUANTITY, PRICE, DATE) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int i) throws SQLException {
                PortfolioMovement movement = movements.get(i);
                pstmt.setInt(1, movement.getUserId());
                pstmt.setString(2, movement.getTicker());
                pstmt.setInt(3, movement.getQuantity());
                pstmt.setDouble(4, movement.getPrice());
                pstmt.setDate(5, java.sql.Date.valueOf(movement.getDate()));
            }

            @Override
            public int getBatchSize() {
                return movements.size();
            }
        });
    }
}
