package com.technohest.core.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.view.RRRGameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Creates a server with specified port and registers packets the server will be listening to.
 * Created by time on 2015-05-05.
 */
public class RServer {
    private boolean gameRunning;
    private Server server;
    private RRRGameModel model = new RRRGameModel();
    private ArrayList<ActionPlayer> actionsToBePerformed = new ArrayList<ActionPlayer>();

    private RRRGameView view = new RRRGameView(model);

    public RServer(String port) {
        server = new Server();
        registerPackets();

        ServerNetworkListener serverNetworkListener = new ServerNetworkListener();
        serverNetworkListener.init(this);

        server.addListener(serverNetworkListener);
        server.start();

        try {
            server.bind(Integer.parseInt(port), Integer.parseInt(port)+1);
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    private void registerPackets() {
        NetworkManger.registerPackets(server);
    }

    public void startGame(HashMap<Integer, Integer> playerIdTypeMap) {
        model.init(playerIdTypeMap);
        model.generateWorld();
        ScreenHandler.getInstance().setGameScreen(view);
        ScreenHandler.getInstance().setScreen(SCREEN.GAME);
        gameRunning = true;

        (new Thread() {
            private long time = System.currentTimeMillis();
            private long elapsedTime = System.currentTimeMillis();
            private long acc = elapsedTime-time;

            @Override
            public void run() {
                while (gameRunning) {
                    if (acc >= 1000/60) {
                        performActions();
                        model.step(acc);
                        time = elapsedTime;
                    }
                    elapsedTime = System.currentTimeMillis();
                    acc = elapsedTime - time;
                }
            }
        }).start();
    }

    private void performActions() {
        if (actionsToBePerformed.size() == 0)
            return;

        for (ActionPlayer ap: actionsToBePerformed) {
            model.performAction(ap.getId(), ap.getAction());
            //Log.info("PERFORMING SOME ACTION ON SERVER...");
        }

        Packet.Packet1Correction p = new Packet.Packet1Correction();
        p.actions = actionsToBePerformed;
        server.sendToAllUDP(p);

        actionsToBePerformed.clear();
    }

    public void gameOver() {
        gameRunning = false;
    }

    public void addActions(Vector<Action> action, int id) {
        for (Action a: action) {
            actionsToBePerformed.add(new ActionPlayer(a, id));
        }
    }
}
