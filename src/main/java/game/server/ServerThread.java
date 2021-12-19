package game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.SocketException;

public class ServerThread implements Runnable {
    private final BufferedReader input;
    private final BufferedWriter output;

    private final Server server;

    private Boolean isTwo = false;

    public ServerThread(BufferedReader input, BufferedWriter output, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                server.sendMessage(message, this);
                if (server.getClients().size() == 2 && !isTwo) {
                    isTwo = true;
                    server.sendCreateEnemies();
                }
            }
        } catch (SocketException socketException) {
            server.removeClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
