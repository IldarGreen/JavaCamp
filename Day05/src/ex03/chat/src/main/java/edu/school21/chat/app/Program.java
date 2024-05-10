package edu.school21.chat.app;


import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.JDBCDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        JDBCDataSource dataSource = new JDBCDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());

        Optional<Message> messageOptional = null;
        try {
            messageOptional = messagesRepository.findById(5L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye!");
//            message.setText(null);
//            message.setLocalDateTime(null);
            messagesRepository.update(message);
        }
    }

}
