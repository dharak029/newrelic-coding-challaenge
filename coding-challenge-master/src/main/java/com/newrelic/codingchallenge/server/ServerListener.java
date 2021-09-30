package com.newrelic.codingchallenge.server;

import com.newrelic.codingchallenge.clienthelper.ClientSocketHelper;
import com.newrelic.codingchallenge.thread.ThreadCreation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable{

    private ServerSocket serverSocket;
    private ThreadCreation threadCreation;

    public ServerListener(ServerSocket serverSocket, ThreadCreation threadCreation) {

        this.threadCreation = threadCreation;
        this.serverSocket = serverSocket;
    }

    /*
       this method keeps track of number of client connection. If clients more than 5 try to connect it denies connection.
     */
    @Override
    public void run() {
        Socket clientSocket;
        try {
            while (true) {
                clientSocket = this.serverSocket.accept();
                if (ClientSocketHelper.acceptMoreClients.get()) {
                    threadCreation.createClientHelper(clientSocket);
                } else {
                    threadCreation.createConnectionDeniedHelper(clientSocket);
                }
            }
        } catch (IOException e) {
        }
    }

    public void close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
        }
    }

}
