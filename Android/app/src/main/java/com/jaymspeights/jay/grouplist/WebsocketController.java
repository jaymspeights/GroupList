package com.jaymspeights.jay.grouplist;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by Jay on 3/4/2018.
 */

public class WebsocketController {

    private static WebsocketController instance;

    public static WebsocketController getInstance() {
        if (instance == null)
            instance = new WebsocketController();
        return instance;
    }



    private WebSocketClient ws;
    private WebsocketHandler handler;

    private WebsocketController() {
    }

    public WebsocketController setHandler(WebsocketHandler handler) {
        this.handler = handler;
        return this;
    }

    public void create(String username, String password) {
        String data = "{\"header\":\"create\",\"username\":\""+username+"\",\"password\":\""+password+"\"}";
        ws.send(data);
    }

    public void login(String username, String password) {
        String data = "{\"header\":\"login\",\"username\":\""+username+"\",\"password\":\""+password+"\"}";
        ws.send(data);
    }

    public void connect() {
        URI uri;
        try {
            uri = new URI("ws://192.168.1.191:50075");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String s) {
                String[] message = s.split("\n");
                switch(message[0]) {
                    case "login":
                        handler.error(message[1]);
                        break;

                    case "listAll":
                        handler.all_lists(Arrays.copyOfRange(message, 1, message.length));
                        break;

                    case "error":
                        handler.error(message[1]);
                        break;

                    default:
                        System.out.println(message[1]);
                        break;
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        ws.connect();
    }

}
