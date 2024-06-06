package edu.school21.sockets.servers;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.HistoryMessage;
import edu.school21.sockets.models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private Server server;
    private Scanner in;
    private PrintWriter out;
    private Socket clientSocket;
    private String username;
    private String password;
    private String chatroomName;


    public static final String RESET = "\u001B[0m";/////TODO
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";


    public ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        sendToClient("Hello from Server!", YELLOW);
        String message;
        while (true) {
            sendToClient("Enter one of the next command (number):", CYAN);
            sendToClient("1. Sign Up", RESET);
            sendToClient("2. Log In", RESET);
            sendToClient("3. Exit", RESET);
            try {
                if (in.hasNext()) {
                    message = JMessageWorker.parseToObject(in.nextLine()).getMessage();
                    if (message.equals("1")) {
                        if (signUp()) {
                            System.out.println("User '" + username + "' created.");
                            sendToClient("You successfully sign up.", BLUE);
                        } else {
                            sendToClient("You're not sign up. Try again.", PURPLE);
                        }
                    } else if (message.equals("2")) {
                        if (logIn()) {
                            System.out.println("User '" + username + "' log in.");
                            sendToClient("You successfully log in.", BLUE);
                            showHistoryOfMessages();
                            roomsService();
                        } else {
                            sendToClient("You're not logged in. Try again.", PURPLE);
                        }
                    } else if (message.equals("3")) {
                        exit();
                        return;
                    } else {
                        sendToClient("Unknown command.", PURPLE);
                    }
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void roomsService() {
        String message;
        while (true) {
            sendToClient("Enter one of the next command (number):", CYAN);
            sendToClient("1. Create Chatroom", RESET);
            sendToClient("2. Choose Chatroom", RESET);
            sendToClient("3. Log Out", RESET);
            try {
                if (in.hasNextLine()) {
                    message = JMessageWorker.parseToObject(in.nextLine()).getMessage();
                    if (message.equals("1")) {
                        createChatroom();
                        chatting();
                    } else if (message.equals("2")) {
                        chooseChatroom();
                    } else if (message.equals("3")) {
                        System.out.println("User '" + username + "' log out.");
                        username = null;
                        return;
                    } else if (message.isEmpty()) {
                        continue;
                    } else {
                        sendToClient("Wrong command.", PURPLE);
                    }
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean logIn() {
        try {
            sendToClient("Enter username:", CYAN);
            String tempUsername = JMessageWorker.parseToObject(in.nextLine()).getMessage();
            for (ClientHandler clh : server.getClientHandlers()) {
                if (clh.username != null && clh.username.equals(tempUsername)) {
                    sendToClient("User '" + tempUsername + " is already logged in.", PURPLE);
                    return false;
                }
            }
            sendToClient("Enter password:", CYAN);
            password = JMessageWorker.parseToObject(in.nextLine()).getMessage();

            if (server.getUsersService().logIn(tempUsername, password)) {
                username = tempUsername;
                return true;
            }
        } catch (RuntimeException e) {
            username = null;
            sendToClient("You're not sign up. " + e.getMessage() + "Try again.", PURPLE);
        }
        return false;
    }

    private boolean signUp() {
        boolean result = false;
        try {
            sendToClient("Enter username:", CYAN);
            String tempUsername = JMessageWorker.parseToObject(in.nextLine()).getMessage();

            if (server.getUsersService().getLogUpUserOnlyName(tempUsername) != null) {
                sendToClient("User's name '" + tempUsername + "' is already taken.", PURPLE);
                return false;
            }

            sendToClient("Enter password:", CYAN);
            password = JMessageWorker.parseToObject(in.nextLine()).getMessage();

            return server.getUsersService().signUp(new User(tempUsername, password));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createChatroom() {
        sendToClient("---- Creating Chatroom ----", RESET);
        sendToClient("Enter name of the Chatroom:", RESET);
        chatroomName = JMessageWorker.parseToObject(in.nextLine()).getMessage();
        User user = server.getUsersService().getLogUpUser(username, password);
        server.getChatroomService().createChatroom(new Chatroom(user.getId(), chatroomName));
        server.getChatrooms().add(chatroomName);
        System.out.println("User: '" + username + "' created '" + chatroomName + "' chatroom.");
        sendToClient("Chatroom '" + chatroomName + "' created.", RESET);
    }

    private void chooseChatroom() {
        List<Chatroom> allChatrooms = server.getChatroomService().getAllRooms();
        int allChatroomsSize = allChatrooms.size();
        if (allChatrooms.isEmpty()) {
            sendToClient("No Chatroom. Not at all. No at single one.", PURPLE);
            return;
        } else {
            sendToClient("Chatrooms:", CYAN);
            for (int i = 0; i < allChatroomsSize; i++) {
                sendToClient(i + 1 + ". " + allChatrooms.get(i).getChatroomname(), RESET);
            }
            sendToClient(allChatroomsSize + 1 + ". Exit", RESET);
        }
        int roomNumder;
        while (true) {
            roomNumder = Integer.parseInt(JMessageWorker.parseToObject(in.nextLine()).getMessage());
            if (0 < roomNumder && roomNumder <= allChatroomsSize) {
                chatroomName = allChatrooms.get(roomNumder - 1).getChatroomname();
                System.out.println("User '" + username + "' entered the '" + chatroomName + "' chat");
                chatting();
                break;
            } else if (roomNumder == allChatroomsSize + 1) {
                return;
            } else {
                sendToClient("Use the correct chat number.", RESET);
            }
        }
    }

    private void chatting() {
        sendToClient("---- Start chatting on: " + chatroomName + " ----", YELLOW);
        sendToChatroom("--< '" + username + "' enter chatroom >--");
        while (true) {
            String message = JMessageWorker.parseToObject(in.nextLine()).getMessage();
            if (message.isEmpty()) {
                continue;
            } else if (message.equalsIgnoreCase("exit")) {
                System.out.println("User '" + username + "' leave '" + chatroomName + "' chatroom");
                sendToChatroom("'" + username + "' left the chatroom");
                break;
            }
            sendToChatroom(message);
        }
    }

    private void showHistoryOfMessages() {
        Chatroom lastVisitedChatroom = server.getChatroomService().getLastVisitedChatroom(username);
        if (lastVisitedChatroom != null) {
            List<HistoryMessage> messages = server.getUsersService()
                    .getFind30MessagesByChatroomName(lastVisitedChatroom.getChatroomname());
            if (!messages.isEmpty()) {
                sendToClient("---- User last visited Chatroom is '" + lastVisitedChatroom.getChatroomname()
                        + "' ----", YELLOW);
                for (HistoryMessage message : messages) {
                    sendToClient(message.getUsername() + ": " + message.getMessageText(), GREEN);
                }
            }
        }
    }

    private synchronized void sendToChatroom(String message) {
        User user = server.getUsersService().getLogUpUser(username, password);
        Chatroom chatroom = server.getChatroomService().getChatroomByName(chatroomName);
        server.getUsersService().saveMessage(user, chatroom, message);
        for (ClientHandler clh : server.getClientHandlers()) {
            if (clh.chatroomName != null && clh.chatroomName.equals(chatroomName) && !clh.equals(this)) {
                clh.sendToClient(username + ": " + message, GREEN);
            }
        }
    }

    private void exit() {
        try {
            if (server.removeClient(this)) {
                System.out.println("User left the chatroom.");
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToClient(String message, String color) {
        out.println(Objects.requireNonNull(JMessageWorker.makeJSONObject(message, color)).toJSONString());

    }

}
