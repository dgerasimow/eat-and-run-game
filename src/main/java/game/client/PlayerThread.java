package game.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.view.GameViewManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class PlayerThread implements Runnable{
    private final BufferedReader input;
    private final BufferedWriter output;
    private final PlayerClient client;
    private final GameViewManager gm;


    public PlayerThread(BufferedReader input, BufferedWriter output, PlayerClient client) {
        this.input = input;
        this.output = output;
        this.client = client;
        gm = this.client.getGm();
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
                Gson gson = new Gson();

                if(message != null) {
                    HashMap<String, String> serverMessage = gson.fromJson(message,new TypeToken<HashMap<String, String>>(){}.getType());
//                    System.out.println(serverMessage.get("method"));
                    if (serverMessage.get("method").equals("create") && gm.getEnemy() == null) {
                        gm.createEnemy();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}