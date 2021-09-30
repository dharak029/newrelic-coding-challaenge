package com.newrelic.codingchallenge.client;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener implements Runnable{

    private Socket socket;
    private Scanner scanner;
    private ClientSocket clientSocket;

    private static final int maxStringLength = 9;
    private static final String serverNewlineChar = "\\n";

    public ClientListener(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        this.socket = clientSocket.getSocket();
        this.scanner = clientSocket.getScanner();
    }


    /*
       this method takes input number
     */
    @Override
    public void run() {
        String input;
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                input = scanner.nextLine();
                input = validateNumber(input);
                out.println(input);
            }
        } catch (IOException e) {}
    }

    /*
         this method validates the number given on input
     */
    private String validateNumber(String number) {

        String trimInput = number.replaceAll("\\s+", "");

        int inputLength = String.valueOf(trimInput).length();

        if (inputLength > maxStringLength) {
            clientSocket.terminateClient();
        }

        String validInput = "";

        if (StringUtils.isNumeric(trimInput)) {
            validInput = StringUtils.leftPad(trimInput, maxStringLength, "0")
                    .concat(serverNewlineChar);
        } else if (trimInput.equals("terminate")) {
            validInput = trimInput.concat(serverNewlineChar);
        } else {
            clientSocket.terminateClient();
        }

        return validInput;
    }
}
