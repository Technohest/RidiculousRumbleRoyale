package com.technohest.core.network;

import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IState;
import com.technohest.LibgdxService.StateGDX;
import com.technohest.core.model.Action;
import com.technohest.core.model.RRRGameModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a server with specified port and registers packets the server will be listening to.
 * @author David Ström
 */
public class RServer {
    private Server server;
    ServerNetworkListener serverNetworkListener = new ServerNetworkListener();

    private RRRGameModel model = new RRRGameModel(new GameLogicGDX());

    //Game loop
    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;
    private boolean gameRunning;

    private ArrayList<Action> actionsToBePerformed = new ArrayList<Action>();

    /**
     * Creates a new Server, registers packets, and start listening to the specified port.
     * @param port
     */
    public RServer(String port) {
        server = new Server();
        registerPackets();

        serverNetworkListener.init(this);

        server.addListener(serverNetworkListener);
        server.start();

        try {
            int tmp = Integer.parseInt(port) + 1;
            server.bind(Integer.parseInt(port), tmp);
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
        }

        Log.set(Log.LEVEL_NONE);
    }

    /**
     * Generates the inital state.
     */
    private void init() {
        generateState();
    }

    private void registerPackets() {
        NetworkManger.registerPackets(server);
    }

    /**
     * Starts the game on the server.
     * @param playerIdTypeMap map of the client id's and the the character type.
     */
    public void startGame(HashMap<Integer, Integer> playerIdTypeMap) {
        model.init(playerIdTypeMap);
        model.generateWorld();
        init();
        gameRunning = true;

        /**
         * Perform player actions and updates the world for every "step" that has passed.
         */
        (new Thread() {
            @Override
            public void run() {
                while (gameRunning) {
                    double newTime = TimeUtils.millis() / 1000.0;
                    double frameTime = Math.min(newTime - currentTime, 0.25);

                    currentTime = newTime;
                    accumulator += frameTime;

                    while (accumulator >= step) {
                        performActions();
                        model.step(step);
                        generateState();
                        serverNetworkListener.sendCorrectionToClients();
                        accumulator -= step;
                    }
                }
            }
        }).start();
    }

    /**
     * Perform the actions sent in by the clients.
     */
    private synchronized void performActions() {
        for (Action a: actionsToBePerformed) {
            model.performAction(a);
        }
        actionsToBePerformed.clear();
    }

    /**
     * Updates the state to be the current physics state.
     */
    public void generateState(){

        model.generateState();
    }

    public void gameOver() {
        gameRunning = false;
    }

    public synchronized void addActionToBePerformed(Action a) {
        actionsToBePerformed.add(a);
    }
}
