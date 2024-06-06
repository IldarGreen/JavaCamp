package edu.school21.sockets.servers;


import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.sockets.services.ChatroomService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class Server {
    private final UsersService usersService;
    private ChatroomService chatroomService;
    private UsersRepositoryJdbcTemplateImpl usersRepository;
    private ServerSocket serverSocket;
    private static List<ClientHandler> clientHandlers = new ArrayList<>();
    private static List<String> chatrooms = new ArrayList<>();

    @Autowired
    public Server(UsersService usersService, ChatroomService chatroomService) {
        this.usersService = usersService;
        this.chatroomService = chatroomService;
    }

    public void start(int port) {
        Socket clientSocket;

        try {
            serverSocket = new ServerSocket(port);
            new Thread(new WaitExitThread()).start();
            while (true) {
                clientSocket = serverSocket.accept();//////throw ex
                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
                if (!addToClientHandlers(clientHandler)) {
                    System.err.println("Client was not added");
                }
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            stop();
        }
    }

    private class WaitExitThread implements Runnable {
        Scanner scanner = new Scanner(System.in);

        @Override
        public void run() {
            while (true) {
                if (scanner.nextLine().equals("exit")) {
                    Server.this.stop();
                }
            }
        }
    }

    public void stop() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public ChatroomService getChatroomService() {
        return chatroomService;
    }

    public List<String> getChatrooms() {
        return chatrooms;
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public boolean addToClientHandlers(ClientHandler clientHandler) {
        return clientHandlers.add(clientHandler);
    }

    public boolean removeFromClientHandlers(ClientHandler clientHandler) {
        return clientHandlers.remove(clientHandler);
    }

    public boolean removeClient(ClientHandler client) {
        return clientHandlers.remove(client);
    }

}
