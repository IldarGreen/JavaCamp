package edu.school21.chat.repositories;


import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> userList = new ArrayList<>();

        String SQLQuery = "SELECT u.id as user_id, " +
                "u.login, " +
                "u.password, " +
                "cs.id as cerated_room_id, " +
                "cs.name as cerated_room_name, " +
                "ms.room_id as active_room_id, " +
                "cs.name as active_room_name " +
                "FROM(SELECT * FROM chat.users) u " +
                "LEFT JOIN chat.messages ms ON ms.author_id = u.id " +
                "LEFT JOIN chat.chatrooms cs ON cs.owner_id = u.id " +
                "LEFT JOIN chat.users us ON us.id = cs.owner_id " +
                "ORDER BY u.id, cs.id, ms.room_id " +
                "LIMIT ? OFFSET ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SQLQuery);) {
            int offset = (page - 1) * size;
            ps.setLong(1, size);
            ps.setLong(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long userId;
                long createdChatId;
                long usedChatId;
                User user;
                Chatroom chat;

                userId = rs.getLong(1);
                if (userList.stream().noneMatch(u -> u.getId().equals(userId))) {
                    user = new User(userId, rs.getString(2), rs.getString(3), new ArrayList<>(), new ArrayList<>());
                    userList.add(user);
                } else {
                    user = userList.stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList()).get(0);
                }

                createdChatId = rs.getLong(4);
                if (createdChatId != 0 && user.getCreatedRooms().stream()
                        .noneMatch(c -> c.getId().equals(createdChatId))) {
                    chat = new Chatroom(createdChatId, rs.getString(5),
                            new User(user.getId(), user.getLogin(), user.getPassword()), null);
                    user.getCreatedRooms().add(chat);
                }

                usedChatId = rs.getLong(6);
                if (usedChatId != 0 && user.getChatedRooms().stream()
                        .noneMatch(c -> c.getId().equals(usedChatId))) {
                    chat = new Chatroom(usedChatId, rs.getString(7), new User(rs.getLong(1), rs.getString(2),
                                    rs.getString(3)), null);
                    user.getChatedRooms().add(chat);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }
}
