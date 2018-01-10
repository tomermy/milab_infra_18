package com.inclass.tomermai.pushclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String POST_STOCK_PRICE_KEY = "postStockPrice";
    public static final String LAST_UPDATED_KEY = "mostRecentStock";
    public static final String STOCK_NOT_FOUND_KEY = "notFound";
    public static final String PRICE_KEY = "currentValue";
    public static final String SEND_STOCK_KEY = "setStockName";

    private Socket mSocketIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SocketIOClient app = (SocketIOClient) getApplication();
        mSocketIO = app.getSocketIOInstance();
        Log.d(TAG, "Connected to the server successfully.");

        if (mSocketIO != null) {

            // todo: Validate listeners during integration with Gilad
            mSocketIO.on(POST_STOCK_PRICE_KEY, onPostStockPrice);
            mSocketIO.on(STOCK_NOT_FOUND_KEY, onStockNotFound);
            mSocketIO.on(Socket.EVENT_CONNECT, onConnect);
            mSocketIO.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocketIO.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocketIO.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            /// ------------------------------------------

            mSocketIO.connect();
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Connection is Established");
                    EditText inputFromUser = findViewById(R.id.stock_name_box);
                    inputFromUser.setText(R.string.default_stock_name);
                    postStockNameToServer();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Encountered a connection Error");
                }
            });
        }
    };

    public void postStockNameToServer() {
        Log.d(TAG, "Sending stock name to Socket");
        EditText inputFromUser = findViewById(R.id.stock_name_box);
        if (inputFromUser != null) {
            mSocketIO.emit(SEND_STOCK_KEY, inputFromUser.getText());
        }
    }

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Connection was Disconnected");
                }
            });
        }
    };

    private Emitter.Listener onPostStockPrice = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        extractPriceAndDate(data);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON for stock price");
                    }
                }

                private void extractPriceAndDate(JSONObject data) throws JSONException {
                    String priceKey = data.getString(PRICE_KEY);
                    String lastUpdatedTime = data.getString(LAST_UPDATED_KEY);
                    Log.d(TAG, "Price is: " + priceKey +
                            ", recently updated on: " + lastUpdatedTime);
                }
            });
        }
    };

    private Emitter.Listener onStockNotFound = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Resource was not found");
                }
            });
        }
    };

    public void onDestroy() {
        super.onDestroy();

        // Close socket and remove all listeners
        mSocketIO.disconnect();
        mSocketIO.off(POST_STOCK_PRICE_KEY, onPostStockPrice);
        mSocketIO.off(STOCK_NOT_FOUND_KEY, onStockNotFound);
        mSocketIO.off(Socket.EVENT_CONNECT, onConnect);
        mSocketIO.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocketIO.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocketIO.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
    }
}
