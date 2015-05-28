package com.technohest.core.network;

import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
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
    private boolean gameRunning;
    private Server server;
    private RRRGameModel model = new RRRGameModel();

    //TIMESTEP
    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;

    private ArrayList<Action> actionsToBePerformed = new ArrayList<Action>();

    ServerNetworkListener serverNetworkListener = new ServerNetworkListener();

    public RServer(String port) {
        server = new Server();
        registerPackets();

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
        generateState();
    }

    private void registerPackets() {
        NetworkManger.registerPackets(server);
    }

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
                        accumulator -= step;
                    }
                }
            }
        }).start();
    }

    /**
     * Perform the actions sent in by the clients. Send the list of actions performed by the server to all the clients.
     */
    private synchronized void performActions() {
        for (Action a: actionsToBePerformed) {
            model.performAction(a);
            if (a.getActionID() == Action.ActionID.ATTACK_BASE)
                System.out.println(a.getActionID());
        }

        generateState();

        Packet.Packet1Correction p = new Packet.Packet1Correction();
        p.state = StateGDX.getInstance();
        p.actions = this.actionsToBePerformed;

        server.sendToAllTCP(p);
        this.actionsToBePerformed.clear();
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
