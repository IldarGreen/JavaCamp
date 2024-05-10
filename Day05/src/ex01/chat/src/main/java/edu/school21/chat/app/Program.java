package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.JDBCDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;


import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        JDBCDataSource dataSource = new JDBCDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());

        Scanner scanner = new Scanner(System.in);
        String input;
        Long massageId;
        while (true) {
            System.out.println("Enter a message ID");
            try {
                input = scanner.nextLine();
                if (input.equals("exit") || input.equals(("q"))) {
                    System.exit(0);
                }
                massageId = Long.parseLong(input);
                Optional<Message> message = messagesRepository.findById(massageId);

                if (message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Message not found");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

// java -jar target/ex01-1.0-SNAPSHOT.jar
