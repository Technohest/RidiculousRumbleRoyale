package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;
import com.technohest.core.model.Action;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Character;
import com.technohest.core.view.RRRGameView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

/**
 * Creates a client with specified port and/or ip and registers the packets the client will listen to.
 * Created by time on 2015-05-05.
 */
public class RClient {
    private Client client;

    //The MVC
    private RRRGameModel model = new RRRGameModel();
    private RRRGameController controller = new RRRGameController(model);
    private RRRGameView view = new RRRGameView(controller, model);

    private int lastSequenceNumber = -1;

    private Integer id;


    private boolean host;
    private IState current;
    private IState previous;

    private void init() {
        current = StateGDX.getInstance();
        previous = current;
        generateState();
    }

    public RClient(String ip, String port) {
        host = true;
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client);
        controller.init(clientNetworkListener);

        client.addListener(clientNetworkListener);
        client.start();

        try {
            int tmp = Integer.parseInt(port) + 1;
            System.out.println("TCP port: " + Integer.parseInt(port));
            System.out.println("UDP port: " + tmp);
            client.connect(5000, ip, Integer.parseInt(port), Integer.parseInt(port)+1);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    public RClient(String port) {
        host = true;
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client);
        controller.init(clientNetworkListener);

        client.addListener(clientNetworkListener);
        client.start();

        try {
            int tmp = Integer.parseInt(port) + 1;
            System.out.println("TCP port: " + Integer.parseInt(port));
            System.out.println("UDP port: " + tmp);
            client.connect(5000, "127.0.0.1", Integer.parseInt(port), tmp);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    private void registerPackets() {
        NetworkManger.registerPackets(client);
    }

    public void startGame(HashMap<Integer, Integer> playerIdTypeMap, Integer id) {
        this.id = id;
        model.setMyID(id);
        model.init(playerIdTypeMap);
        model.generateWorld();
        model.setIsClient();
        init();
        ScreenHandler.getInstance().setGameScreen(view);
        ScreenHandler.getInstance().setScreen(SCREEN.GAME);
        Log.info("STARTING GAME ON CLIENT.");
    }

    public boolean isHost() {
        return host;
    }

    /**
     * Generates state using the current characters
     */
    public void generateState(){
        current.setState(model.getGameLogic().generateState());
    }

    public void correct(IState state, ArrayList<Action> actions, Vector<Action> playerActions) {
        if (!this.current.equals(state)) {
            model.correct(state);
            applyServerActions(actions);
            reapplyLocalActions(playerActions);
        }
        this.current.setState(state.getState());
    }

    /**
     * Applies the local actions which haven't been performed yet by the server.
     * @param playerActions
     */
    private void reapplyLocalActions(Vector<Action> playerActions) {
        for (Action a: playerActions) {
            model.performAction(a);
        }
    }

    /**
     * Apply the actions performed by the server.
     * @param actions
     */
    private void applyServerActions(ArrayList<Action> actions) {
        for (Action a: actions) {
            model.performAction(a);
        }
    }
}
