package com.newrelic.codingchallenge.clienthelper;

import com.newrelic.codingchallenge.Main;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientSocketHelper {

    private static List<Socket> clientSocketList = new ArrayList<>();
    /*
       I have used AtomicBoolean rather than simple boolean as it is threadsafe.
     */
    public static AtomicBoolean acceptMoreClients = new AtomicBoolean(true);

    private ClientSocketHelper() {
    }

    /*
        allows more client connection till it reaches limit
     */
    public static void addSocket(Socket s) {
        clientSocketList.add(s);
        if (clientSocketList.size() >= Main.maxClientConnection) {
            acceptMoreClients.set(false);
        }
    }

    /*
        removes client connection
     */
    public static void removeSocket(Socket s) {
        clientSocketList.remove(s);
        if (clientSocketList.size() < Main.maxClientConnection) {
            acceptMoreClients.set(true);
        }
    }

    public static void terminateAllClientConnections() {

        clientSocketList.forEach(s -> {
            try {
                s.close();
                System.out.println(
                        "Connection closed for client: " + s.getInetAddress().getHostAddress());
            } catch (IOException e) {
            }
        });
        clientSocketList.clear();
        System.out.println("Client connections shut down");
    }

}
