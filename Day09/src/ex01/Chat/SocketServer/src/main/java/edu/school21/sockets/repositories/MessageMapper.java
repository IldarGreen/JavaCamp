package edu.school21.sockets.repositories;


import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(rs.getLong("sender_id"),
                rs.getString("message_text"),
                rs.getTimestamp("sending_time").toLocalDateTime());
    }
}
