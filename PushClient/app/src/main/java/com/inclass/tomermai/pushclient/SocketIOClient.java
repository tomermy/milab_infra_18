package com.inclass.tomermai.pushclient;


import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketIOClient extends Application {

    // todo: Replace with the correct URL of our server
    private static final String PUSH_SERVER_URL = "heroku server URL";

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(PUSH_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocketIOInstance() {
        return mSocket;
    }

}
