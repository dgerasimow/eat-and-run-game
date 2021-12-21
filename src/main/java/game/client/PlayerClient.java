package game.client;

import game.view.GameViewManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PlayerClient {
    private Socket socket;
    private PlayerThread gameThread;
    private GameViewManager gm;
    private String serverIp;

    public PlayerClient(GameViewManager gm, String serverIp) {
        this.gm = gm;
        this.serverIp = serverIp;
    }

    public void sendMessage(String message) {
        try {
            gameThread.getOutput().write(message);
            gameThread.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        socket = new Socket(serverIp, 5555);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        gameThread = new PlayerThread(input, output, this);

        new Thread(gameThread).start();
    }

    public GameViewManager getGm() {
        return gm;
    }
}