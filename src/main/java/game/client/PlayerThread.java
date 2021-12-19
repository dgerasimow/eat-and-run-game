package game.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
                    HashMap<String, String> serverMessage = gson.fromJson(message, new TypeToken<HashMap<String, String>>(){}.getType());
                    String method = serverMessage.get("method");
                    if (method.equals("create")) {
                        if (gm.getHostGameController() != null && gm.getHostGameController().getEnemy() == null) {
                            gm.getHostGameController().createEnemy();
                        } else if (gm.getHostGameController() == null && gm.getConnectedGameController().getEnemy() == null) {
                            gm.getConnectedGameController().createEnemy();
                        }
                    } else if (method.equals("newFood")) {
                        gm.getConnectedGameController().spawnFood(Integer.parseInt(serverMessage.get("foodId")),
                                Double.parseDouble(serverMessage.get("x")),
                                Double.parseDouble(serverMessage.get("y")));
                    } else if (method.equals("updateTime")) {
                        gm.getConnectedGameController().updateTime(serverMessage.get("parameter"));
                    } else if (method.equals("move")) {
                        if (gm.getHostGameController() == null) {
                            gm.getConnectedGameController().moveEnemy(Double.parseDouble(serverMessage.get("x")),
                                    Double.parseDouble(serverMessage.get("y")));
                        } else if (gm.getHostGameController() != null) {
                            gm.getHostGameController().moveEnemy(Double.parseDouble(serverMessage.get("x")),
                                    Double.parseDouble(serverMessage.get("y")));
                        }
                    } else if (method.equals("eat")) {
                        if (gm.getHostGameController() == null) {
                            gm.getConnectedGameController().enemyEatFood(Integer.parseInt(serverMessage.get("foodId")));
                        } else {
                            gm.getHostGameController().enemyEatFood(Integer.parseInt(serverMessage.get("foodId")));
                        }
                    } else if (method.equals("exit")) {
                        System.out.println("EXITING");
                        gm.exitGame();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}