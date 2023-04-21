package com.stockify.stockifyapp.repository;

import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.model.User;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SignedMessageRowMapper implements RowMapper<SignedMessage> {

    @Override
    public SignedMessage mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("ID");
        LocalDateTime timestamp = resultSet.getObject("TIMESTAMP", LocalDateTime.class);
        Integer conversationId = resultSet.getInt("CONVERSATION_ID");
        String message = resultSet.getString("MESSAGE");
        AggregateReference<User, Integer> user = AggregateReference.to(resultSet.getInt("USER_ID"));

        SignedMessage signedMessage = new SignedMessage(timestamp, conversationId, message, user);
        signedMessage.setId(id);
        return signedMessage;
    }
}
