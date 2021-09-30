package com.newrelic.codingchallenge.clienthelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionDeniedHelper implements Runnable{

    private Socket socket;

    public ConnectionDeniedHelper(Socket clientSocket) {
        this.socket = clientSocket;
    }

    /*
        this will be called when number of client connection exceeds 5 to deny more connection
     */
    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Connection Denied");
            System.out.println("Connection denied to client : " + socket.getInetAddress().getHostAddress());
        } catch (IOException e) {}

    }

}
