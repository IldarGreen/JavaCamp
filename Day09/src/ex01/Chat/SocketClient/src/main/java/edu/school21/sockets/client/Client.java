package edu.school21.sockets.client;

import javax.net.SocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    public Client(String ip, int port) throws IOException {
        socket = SocketFactory.getDefault().createSocket(ip, port);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        new Thread(new ReceiveMessageFromServer(out, in, socket)).start();
        new Thread(new SendMessageToServer(out, in, socket)).start();
    }

    private class ReceiveMessageFromServer extends Thread {
        private Socket socket;
        private PrintWriter out;
        private Scanner in;

        public ReceiveMessageFromServer(PrintWriter out, Scanner in, Socket socket) {
            this.out = out;
            this.in = in;
            this.socket = socket;
        }

        Scanner scanner = new Scanner(System.in);

        @Override
        public void run() {
            try {
                sendMessage();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private void sendMessage() throws IOException {
            while (true) {
                String message = scanner.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("eexit")) {
                    break;
                }
            }
            close(out, in, socket, false);
        }
    }

    public class SendMessageToServer extends Thread {
        private Socket socket;
        private PrintWriter out;
        private Scanner in;

        public SendMessageToServer(PrintWriter out, Scanner in, Socket socket) {
            this.out = out;
            this.in = in;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                receiveMessage();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void receiveMessage() throws IOException, InterruptedException {
            while (in.hasNextLine()) {
                String message = in.nextLine();
                System.out.println(message);
            }
            close(out, in, socket, true);
        }
    }

    public synchronized void close(PrintWriter out, Scanner in, Socket socket, boolean serverClosed) throws IOException {
        in.close();
        out.close();
        socket.close();
        if (serverClosed) {
            System.out.println("Server is closed.");
        }
        System.exit(0);
    }
}