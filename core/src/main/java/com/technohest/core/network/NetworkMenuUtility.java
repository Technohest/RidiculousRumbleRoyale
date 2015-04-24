package com.technohest.core.network;

/**
 * Manages the ip, port, and if the current session is a server.
 * Created by time on 2015-04-21.
 */
public class NetworkMenuUtility {
    private String ip;
    private String port;
    private Boolean isServer;
    private static NetworkMenuUtility instance;

    /**
     * Creates the NetworkMenuUtility if not done and returns the instance of the NetworkMenuUtility
     * @return instance The only instance of this object
     */
    public static NetworkMenuUtility getInstance() {
        if (instance == null) {
            instance = new NetworkMenuUtility();
        }
        return instance;
    }

    ////////////////SETTERS AND GETTERS\\\\\\\\\\\\\\\\\\
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setIsServer(Boolean isServer) {
        this.isServer = isServer;
    }

    public Boolean isServer() {
        return isServer;
    }

    /**
     * Resets the state.
     */
    public void reset() {
        ip = "";
        port = "";
        isServer = null;
    }
}
