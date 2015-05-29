package com.technohest.core.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.technohest.LibgdxService.IState;
import com.technohest.LibgdxService.StateGDX;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.event.REventListener;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.view.RRRGameView;
import java.io.IOException;
import java.util.*;

/**
 * Creates a client with specified port and/or ip and registers the packets the client will listen to.
 * @author David Ström
 */
public class RClient {
    private Client client;

    //The MVC
    private RRRGameModel model = new RRRGameModel();
    private RRRGameController controller = new RRRGameController(model);
    private RRRGameView view = new RRRGameView(controller, model);

    //All classes listening to when the game starts on the client.
    private ArrayList<REventListener> listeners = new ArrayList<REventListener>();

    //
    private boolean host;
    private IState current;
    private IState previous;

    private void init() {
        current = StateGDX.getInstance();
        previous = current;
        generateState();
    }

    /**
     * Creates a client which connects to a remote host. Also registers all packets which will be sent over the network.
     * @param ip
     * @param port
     */
    public RClient(String ip, String port) {
        host = true;
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client, controller);
        controller.addEventListener(clientNetworkListener);

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

    /**
     * Creates a client which connects to the local host. Also registers all packets which will be sent over the network.
     * @param port
     */
    public RClient(String port) {
        host = true;
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client, controller);
        controller.addEventListener(clientNetworkListener);

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

    /**
     * Starts the game and notifies all listening classes.
     * @param playerIdTypeMap the map of which id corresponds to what type of character.
     * @param id the local user id.
     */
    public void startGame(HashMap<Integer, Integer> playerIdTypeMap, Integer id) {
        model.setMyID(id);
        model.init(playerIdTypeMap);
        model.generateWorld();

        init();

        for (REventListener listener: listeners) {
            listener.fireEvent();
        }
    }

    /**
     * @return
     * true if hostring the game.
     */
    public boolean isHost() {
        return host;
    }

    /**
     * Generates state using the current characters
     */
    public void generateState(){
       model.generateState();
    }

    public RRRGameView getView() {
        return view;
    }

    public void addEventListener(REventListener eventListener) {
        listeners.add(eventListener);
    }
}
