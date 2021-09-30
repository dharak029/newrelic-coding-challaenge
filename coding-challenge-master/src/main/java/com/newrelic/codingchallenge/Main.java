package com.newrelic.codingchallenge;

import java.net.InetAddress;
import java.net.ServerSocket;

import com.newrelic.codingchallenge.clienthelper.ClientHelper;
import com.newrelic.codingchallenge.numberprocessing.Cache;
import com.newrelic.codingchallenge.numberprocessing.CheckDuplicate;
import com.newrelic.codingchallenge.thread.ThreadCreation;


public class Main {

    private static Main mainObject;
    private ServerSocket serverSocket;

    public static int maxClientConnection;
    private int threadPoolSize;
    private ThreadCreation threadCreation;

    /*
            Server starts at port 40000
     */
    public Main(String ipAddress) throws Exception {

        this.serverSocket = new ServerSocket(4000, 0, InetAddress.getByName(ipAddress));

        maxClientConnection = 5;
        threadPoolSize = 10;
        threadCreation = ThreadCreation.getThreadCreationObject(threadPoolSize);
        Cache.getCacheObject();
        CheckDuplicate.getCheckDuplicateObject();
    }


    public static void main(String[] args) throws Exception {


        System.out.println("Starting up com.newrelic.codingchallenge.server ....");

        // Add your code here
        mainObject = new Main(args[0]);
        System.out.println("Host = " + mainObject.getSocketAddress().getHostAddress());
        System.out.println("Port = " + mainObject.getPort());
        mainObject.createThreads();

        while (true) {
            if (ClientHelper.initiateShutdown.get()) {
                mainObject.serverShutDown();
            }
            Thread.sleep(1);
        }
    }

    /*
          this method creates threads for server, log file creation and report generation
     */
    private void createThreads() {
        threadCreation.createServerListener(serverSocket);
        threadCreation.createSaveNumbersToFile();
        threadCreation.createReportGeneration();
    }

    public void serverShutDown() {
        threadCreation.terminateThreads();
        System.out.println("Server is shutting down.");
        System.exit(0);
    }

    public InetAddress getSocketAddress() {
        return this.serverSocket.getInetAddress();
    }

    public int getPort() {
        return this.serverSocket.getLocalPort();
    }
}