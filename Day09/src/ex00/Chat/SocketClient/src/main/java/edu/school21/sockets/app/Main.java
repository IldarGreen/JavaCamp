package edu.school21.sockets.app;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;

import java.io.IOException;


@Parameters(separators = "=")
public class Main {
    @Parameter(names = {"--server-port"})
    int port;
    String ip = "127.0.0.1";

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public void run() {
        try {
            Client client = new Client(ip, port);
            client.run();
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
