package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class ChatroomRepositoryImpl implements ChatroomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatroomRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        new InitRepository(dataSource).initDatabase("chatrooms_init.sql");
    }

    @Override
    public Optional<Chatroom> findByChatroomName(String chatroomname) {
        String SQLquery = "SELECT * FROM chatrooms WHERE chatroomname = ?";
        return jdbcTemplate.query(SQLquery, new Object[]{chatroomname}, new ChatroomMapper()).stream().findAny();
    }

    @Override
    public Optional findById(Long id) {
        String SQLquery = "SELECT * FROM chatrooms WHERE id = " + id;
        return Optional.of((Chatroom) jdbcTemplate.query(SQLquery, new ChatroomMapper()));
    }

    @Override
    public List<Chatroom> findAll() {
        String SQLquery = "SELECT * FROM chatrooms";
        return jdbcTemplate.query(SQLquery, new ChatroomMapper());
    }

    @Override
    public void save(Chatroom entity) {
        String SQLquery = "INSERT INTO chatrooms (creator_id, chatroomname) VALUES (?, ?) ";
        if (jdbcTemplate.update(SQLquery, entity.getCreator_id(), entity.getChatroomname()) == 0) {
            System.err.println("Chatroom '" + entity + "' not saved.");
        }
    }

    @Override
    public void update(Chatroom entity) {
        String SQLquery = "UPDATE chatrooms SET creator_id = ?, chatroomname = ? WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, entity.getCreator_id(), entity.getChatroomname(), entity.getId()) == 0) {
            System.err.println("Chatroom '" + entity + "' not update.");
        }
    }

    @Override
    public void delete(Long id) {
        String SQLquery = "DELETE FROM chatrooms WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, id) == 0) {
            System.err.println("User with id: " + id + " not found.");
        }
    }

    @Override
    public Optional<Chatroom> findLastVisitedChatroom(String username) {
        String SQLquery = "SELECT chatrooms.id, creator_id, chatroomname FROM chatrooms " +
                "JOIN messages on messages.chatroom_id = chatrooms.id " +
                "JOIN users on users.id = messages.sender_id " +
                "WHERE username = ? " +
                "ORDER BY sending_time DESC LIMIT 1;";
        return jdbcTemplate.query(SQLquery, new Object[]{username}, new ChatroomMapper()).stream().findAny();
    }

}
