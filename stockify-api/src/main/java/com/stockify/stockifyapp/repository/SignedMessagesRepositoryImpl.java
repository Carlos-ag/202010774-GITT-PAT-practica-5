package com.stockify.stockifyapp.repository;

import com.stockify.stockifyapp.model.SignedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SignedMessagesRepositoryImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SignedMessage> findLatestMessagesByUserId(Integer userId) {
        String sql = "SELECT t1.* FROM SIGNED_MESSAGES AS t1 JOIN (SELECT CONVERSATION_ID, MAX(TIMESTAMP) as MAX_TIMESTAMP FROM SIGNED_MESSAGES WHERE USER_ID = ? GROUP BY CONVERSATION_ID) AS t2 ON t1.CONVERSATION_ID = t2.CONVERSATION_ID AND t1.TIMESTAMP = t2.MAX_TIMESTAMP WHERE t1.USER_ID = ?";
        List<SignedMessage> messages = jdbcTemplate.query(sql, new SignedMessageRowMapper(), userId, userId);
        return messages;
    }
}
