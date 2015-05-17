package com.technohest.core.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.technohest.Tools.Sort;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;
import com.technohest.core.model.Action;
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
    private ArrayList<Action> actionsToBePerformed = new ArrayList<Action>();
    private long lastUpdateTime = 0;
    private IState state;

    public RServer(String port) {
        server = new Server();
        registerPackets();

        ServerNetworkListener serverNetworkListener = new ServerNetworkListener();
        serverNetworkListener.init(this);

        server.addListener(serverNetworkListener);
        server.start();

        try {
            int tmp = Integer.parseInt(port) + 1;
            System.out.println("TCP port: " + Integer.parseInt(port));
            System.out.println("UDP port: " + tmp);
            server.bind(Integer.parseInt(port), tmp);
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
        }

        Log.set(Log.LEVEL_INFO);

    }

    private void init() {
        state = StateGDX.getInstance();
        generateState();
    }

    private void registerPackets() {
        NetworkManger.registerPackets(server);
    }

    public void startGame(HashMap<Integer, Integer> playerIdTypeMap) {
        model.init(playerIdTypeMap);
        model.generateWorld();
        model.setIsServer();
        init();
        gameRunning = true;

        (new Thread() {
            private long time = System.currentTimeMillis();
            private long elapsedTime = System.currentTimeMillis();
            private long acc = elapsedTime-time;

            @Override
            public void run() {
                while (gameRunning) {
                    if (acc >= 1000/30) {
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
        actionsToBePerformed = Sort.sortTime(actionsToBePerformed);

        if (actionsToBePerformed.size() == 0)
            return;

        for (Action ap: actionsToBePerformed) {
            if (ap.getTimestamp() < lastUpdateTime) {
                actionsToBePerformed.remove(ap);
                continue;
            }

            model.performAction(ap);
        }

        //set update time to be the time of the last updated action.
        lastUpdateTime = actionsToBePerformed.get(actionsToBePerformed.size()-1).getTimestamp();

        Packet.Packet1Correction p = new Packet.Packet1Correction();
        p.actions = actionsToBePerformed;
        p.state = StateGDX.getInstance();
        server.sendToAllUDP(p);

        actionsToBePerformed.clear();
    }

    public void generateState(){
        state.setState(model.getGameLogic().generateState());
    }

    public void gameOver() {
        gameRunning = false;
    }

    public void addActions(Vector<Action> action) {
        for (Action a: action) {
            if (a.getTimestamp() > lastUpdateTime)
                actionsToBePerformed.add(a);
        }
    }

    /**
     * Sends a request to the client to sync the
     */
    public void sync() {
        Packet.Packet1Correction p = new Packet.Packet1Correction();
        p.state = StateGDX.getInstance();
        server.sendToAllTCP(p);
    }
}
