package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Character;
import com.technohest.core.view.RRRGameView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

    private boolean host;
    private IState state;

    public RClient(String ip, String port) {
        host = false;
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client);
        controller.init(clientNetworkListener);

        client.addListener(clientNetworkListener);
        client.start();

        try {
            client.connect(5000, ip, Integer.parseInt(port), Integer.parseInt(port)+1);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    private void init() {
        state = StateGDX.getInstance();
        generateState();
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
            client.connect(5000, "127.0.0.1", Integer.parseInt(port), Integer.parseInt(port)+1);
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
        model.setMyID(id);
        model.init(playerIdTypeMap);
        model.generateWorld();
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
        state.setState(model.getGameLogic().generateState());
    }

    /**
     * Compares local state to remote state and correct if needed
     * @param state
     * Remote state
     */
    public void correct(IState state) {
        if(!this.state.equals(state)){
            model.correct(state);
        }
        this.state.setState(state.getState());
    }
}
