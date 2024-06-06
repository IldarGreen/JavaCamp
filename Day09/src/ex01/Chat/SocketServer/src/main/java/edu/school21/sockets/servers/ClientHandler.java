package edu.school21.sockets.servers;

import edu.school21.sockets.models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private Scanner in;
    private PrintWriter out;
    private Socket clientSocket;
    private String username;
    private String password;
    private boolean inChat = false;


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
        out.println("Hello from Server!");
        String message;
        while (true) {
            out.println("Available commands: signUp, signIn, exit");
            try {
                if (in.hasNext()) {
                    message = in.nextLine();
                    if (message.equals("signUp")) {
                        if (!signUp()) {
                            out.println("Something went wrong, you're not sign up. Try again.");
                        }
                    } else if (message.equals("signIn")) {
                        if (signIn()) {
                            out.println("Start messaging:");
                            chatting();
                        } else {
                            exit();
                            break;
                        }
                    } else if (message.equals("exit")) {
                        exit();
                        break;
                    } else {
                        out.println("Unknown command.");
                    }
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean signIn() {
        boolean result = false;
        try {
            out.println("Enter username:");
            username = in.nextLine();

            out.println("Enter password:");
            password = in.nextLine();

            return server.getUsersService().signIn(username, password);
        } catch (RuntimeException e) {
            username = null;
            out.println("You're not sign up. " + e.getMessage() + "Try again.e.getMessage()");
        }
        return result;
    }

    private boolean signUp() {
        boolean result = false;
        try {
            out.println("Enter username:");
            username = in.nextLine();

            out.println("Enter password:");
            password = in.nextLine();

            return server.getUsersService().signUp(new User(username, password));
        } catch (RuntimeException e) {
            username = null;
            e.printStackTrace();
        }
        return result;
    }

    private void chatting() {
        inChat = true;
        while (true) {
            String message = in.nextLine();
            if (message.equals("exit")) {
                exit();
                break;
            }
            sendToEveryone(message);
        }
    }

    private synchronized void sendToEveryone(String message) {
        User user = server.getUsersService().getSignInUser(username, password);
        server.getUsersService().saveMessage(user, message);
        System.out.println(message);
        for (ClientHandler clh : server.getClientHandlers()) {
            if (clh.inChat && !clh.equals(this)) {
                clh.out.println(username + ": " + message);
            }
        }
    }

    private void exit() {
        try {
            if (server.removeClient(this)) {
                System.out.println("User left the chat.");
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
    }
}
