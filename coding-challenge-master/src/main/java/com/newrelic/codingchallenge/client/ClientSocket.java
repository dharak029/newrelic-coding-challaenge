package com.newrelic.codingchallenge.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {

    private Socket socket;
    private Scanner scanner;

    public static final int defaultPort = 4000;

    private ClientSocket(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }

    /*
       Main client code
     */
    public static void main(String[] args) throws Exception {

        ClientSocket client =
                new ClientSocket(InetAddress.getByName(args[0]), defaultPort);

        client.createThreads();
    }

    private void createThreads() {

        ClientListener listenerRunnable = new ClientListener(this);
        Thread listener = new Thread(listenerRunnable);
        listener.start();

        ServerResponseHelper serverResponseHelper = new ServerResponseHelper(this);
        Thread responseHandler = new Thread(serverResponseHelper);
        responseHandler.start();

    }

    public void terminateClient() {
        try {
            socket.close();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    public Socket getSocket() {
        return socket;
    }

    public Scanner getScanner() {
        return scanner;
    }

}
