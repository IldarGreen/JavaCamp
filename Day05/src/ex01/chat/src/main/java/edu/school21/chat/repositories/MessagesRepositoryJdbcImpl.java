package edu.school21.chat.repositories;


import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.text.SimpleDateFormat;


public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long massageId) {
        String SQLQuery = "SELECT * FROM chat.messages WHERE id = " + massageId;

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQLQuery);) {
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                return null;
            }
            Long userId = rs.getLong(2);
            Long chatroomId = rs.getLong(3);
            User user = findUserById(userId);
            Chatroom chatroom = findChatroomById(chatroomId);
            String message = rs.getString(4);
            LocalDateTime localDateTime = rs.getTimestamp(5).toLocalDateTime();
            return Optional.of(new Message(massageId, user, chatroom, message, localDateTime));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    private User findUserById(Long id) throws SQLException {
        String SQLQuery = "SELECT * FROM chat.users WHERE id = " + id;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQLQuery);) {
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String login = rs.getString(2);
            String password = rs.getString(3);

            return new User(id, login, password);
        }
    }

    private Chatroom findChatroomById(Long chatroomId) throws SQLException {
        String SQLQuery = "SELECT * FROM chat.chatrooms WHERE owner_id = " + chatroomId;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQuery);) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String name = rs.getString(2);

            return new Chatroom(chatroomId, name);
        }
    }

}
