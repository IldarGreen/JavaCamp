package edu.school21.sockets.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Scanner scanner = new Scanner(System.in);

    public Client(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void receiveMessage() throws IOException {
        String message = in.readLine();
        System.out.println(message);
        if (message.equals("Successful!")) {
            stop();
        }
    }

    public void sendMessage() {
        if (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }

    public void run() throws IOException {
        while (true) {
            receiveMessage();
            sendMessage();
        }
    }

    private void stop() {
        try {
            in.close();
            out.close();
            scanner.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
}
