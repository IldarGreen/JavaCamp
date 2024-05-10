package edu.school21.chat.repositories;


import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;


public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

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

//    @Override //оба варианта рабочие
//    public void save(Message message) {
//        String SQLQuery = "INSERT INTO chat.messages (room_id, author_id, text, datetime) VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(SQLQuery, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setLong(1, message.getChatroom().getId());
//            ps.setLong(2, message.getAuthor().getId());
//            ps.setString(3, message.getText());
//            ps.setTimestamp(4, Timestamp.valueOf(message.getLocalDateTime()));
//
//            ps.execute();
//
//            ResultSet id  = ps.getGeneratedKeys();
//            id.next();
//            message.setId(id.getLong("id"));
//        } catch (SQLException e) {
//            throw new NotSavedSubEntityException();
//        }
//    }

    @Override //оба варианта рабочие
    public void save(Message message) {
        String SQLQuery = "INSERT INTO chat.messages (room_id, author_id, text, datetime) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ps.setLong(1, message.getChatroom().getId());
            ps.setLong(2, message.getAuthor().getId());
            ps.setString(3, message.getText());
            ps.setTimestamp(4, Timestamp.valueOf(message.getLocalDateTime()));

            ResultSet rs = ps.executeQuery();

            rs.next();
            message.setId(rs.getLong("id"));
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }
    }

    @Override
    public void update(Message message) throws NotSavedSubEntityException {
        String SQLQuery = "UPDATE chat.messages SET room_id = ?, author_id = ?, text = ?, datetime = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {

            JDBCDataSource dataSource = new JDBCDataSource();
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
            Optional<Message> messageOptional = messagesRepository.findById(message.getId());
            if (messageOptional.isPresent()) {
                Message messageOld = messageOptional.get();

                ps.setLong(1, message.getChatroom().getId());
                ps.setLong(2, message.getAuthor().getId());
                if (message.getText() == null) {
                    ps.setString(3, messageOld.getText());
                } else {
                    ps.setString(3, message.getText());
                }
                if (message.getLocalDateTime() == null) {
                    ps.setTimestamp(4, Timestamp.valueOf(messageOld.getLocalDateTime()));
                } else {
                    ps.setTimestamp(4, Timestamp.valueOf(message.getLocalDateTime()));
                }
                ps.setLong(5, message.getId());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }
    }

}
