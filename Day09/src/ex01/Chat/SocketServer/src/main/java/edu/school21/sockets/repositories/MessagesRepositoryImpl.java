package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class MessagesRepositoryImpl implements MessagesRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String SQLquery = "SELECT * FROM messages WHERE id = " + id;
        return Optional.of((Message) jdbcTemplate.query(SQLquery, new MessageMapper()));
    }

    @Override
    public List<Message> findAll() {
        String SQLquery = "SELECT * FROM messages";
        return jdbcTemplate.query(SQLquery, new MessageMapper());
    }

    @Override
    public void save(Message entity) {
        String SQLquery = "INSERT INTO messages (sender_id, message_text, sending_time) VALUES (?, ?, ?)";
        if (jdbcTemplate.update(SQLquery, entity.getSender_id(),
                entity.getMessageText(), entity.getSendingTime()) == 0) {
            System.err.println("Message '" + entity + "' not saved.");
        }
    }

    @Override
    public void update(Message entity) {
        String SQLquery = "UPDATE messages SET sender_id = ?, message_text = ?, sending_time = ? WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, entity.getSender_id(), entity.getMessageText(),
                entity.getSendingTime(), entity.getId()) == 0) {
            System.err.println("Message '" + entity + "' not update.");
        }
    }

    @Override
    public void delete(Long id) {
        String SQLquery = "DELETE FROM messages WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, id) == 0) {
            System.err.println("Message with id: " + id + " not found.");
        }
    }
}
