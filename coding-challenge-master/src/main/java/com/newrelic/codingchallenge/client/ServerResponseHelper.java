package com.newrelic.codingchallenge.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerResponseHelper implements Runnable{

    private Socket socket;
    private ClientSocket clientSocket;

    public ServerResponseHelper(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        this.socket = clientSocket.getSocket();
    }

    /*
       Handles server response and act accordingly
     */
    @Override
    public void run() {
        String response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                response = br.readLine();
                if (response == null) {
                    System.out.println("Server is shutting down");
                    clientSocket.terminateClient();
                    break;
                } else if (response.equals("Connected")) {
                    System.out.println("Connected to Server.....");
                } else if (response.equals("Connection Denied")) {
                    System.out.println("Connection Denied by Server");
                    clientSocket.terminateClient();
                }
            }
        } catch (IOException e) {
        }
    }

}
