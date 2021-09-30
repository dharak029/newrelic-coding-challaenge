package com.newrelic.codingchallenge.clienthelper;

import com.newrelic.codingchallenge.numberprocessing.CheckDuplicate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHelper implements Runnable{

    private Socket socket;
    public static final String terminateString = "terminate\\n";

    public static AtomicBoolean initiateShutdown = new AtomicBoolean(false);

    public ClientHelper(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        String data;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("Connected");
            while (true) {
                data = br.readLine();
                if (data == null) {
                    ClientSocketHelper.removeSocket(socket);
                    socket.close();
                    break;
                } else {
                    processNumbers(data);
                }
            }
        } catch (IOException e) {
        }
    }

    private void processNumbers(String data) {
        if (data.equals(terminateString)) {
            initiateShutdown.set(true);
        } else {
            CheckDuplicate.getCheckDuplicateObject().isDuplicate(data);
        }
    }

}
