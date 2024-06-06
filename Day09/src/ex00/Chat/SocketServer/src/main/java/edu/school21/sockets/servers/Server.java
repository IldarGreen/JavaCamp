package edu.school21.sockets.servers;


import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


@Component
public class Server {
    private final UsersService usersService;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        String message;

        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Hello from Server!");
        } catch (IOException e) {
            stop();
            System.out.println(e.getMessage());
        }

        while (true) {
            try {
                message = in.readLine();
                if (!message.equals("signUp")) {
                    out.println("You can only enter \'signUp\'");
                    continue;
                }
                out.println("Enter username:");
                String username = in.readLine();

                out.println("Enter password:");
                String password = in.readLine();

                usersService.signUp(new User(username, password));
                out.println("Successful!");
                stop();
            } catch (RuntimeException | IOException e) {
                System.out.println(e.getMessage());
                out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (clientSocket != null)
                clientSocket.close();
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}
