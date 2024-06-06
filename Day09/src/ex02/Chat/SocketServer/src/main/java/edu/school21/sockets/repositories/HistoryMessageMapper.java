package edu.school21.sockets.repositories;


import edu.school21.sockets.models.HistoryMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class HistoryMessageMapper implements RowMapper<HistoryMessage> {
    @Override
    public HistoryMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HistoryMessage(rs.getString("username"), rs.getString("message_text"));
    }
}
