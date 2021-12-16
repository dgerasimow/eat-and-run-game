package game.server;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int PORT = 5555;
    private ServerSocket socket;
    private final CopyOnWriteArrayList<ServerThread> clients = new CopyOnWriteArrayList<>();


    public void start() throws IOException {
        socket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = socket.accept();

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            ServerThread serverThread = new ServerThread(input, output, this);
            clients.add(serverThread);
            System.out.println("Подключился новый клиент");

            new Thread(serverThread).start();
        }
    }

    public void sendMessage(String message, ServerThread sender) throws IOException {
        for (ServerThread client : clients) {
            if (client.equals(sender)){
                continue;
            }

            client.getOutput().write(message+ "\n");
            client.getOutput().flush();
        }
    }

    public void sendCreateEnemies() throws IOException {
        for (ServerThread client : clients) {
            Gson gson = new Gson();
            HashMap<String, String> message = new HashMap<>();
            message.put("method", "create");
            client.getOutput().write(gson.toJson(message) + "\n");
            client.getOutput().flush();
        }
    }

    public void removeClient(ServerThread serverThread) {
        clients.remove(serverThread);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

    public CopyOnWriteArrayList<ServerThread> getClients() {
        return clients;
    }
}
