package com.newrelic.codingchallenge.thread;

import com.newrelic.codingchallenge.clienthelper.ClientHelper;
import com.newrelic.codingchallenge.clienthelper.ClientSocketHelper;
import com.newrelic.codingchallenge.clienthelper.ConnectionDeniedHelper;
import com.newrelic.codingchallenge.logfile.SaveNumbersToFile;
import com.newrelic.codingchallenge.report.ReportGeneration;
import com.newrelic.codingchallenge.server.ServerListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadCreation {

    private static ThreadCreation threadCreationObject = null;
    private static ExecutorService executorService;

    private Runnable reportGenerationThread;
    private Runnable serverListenerThread;
    private Runnable saveNumbersToFileThread;

    public static ThreadCreation getThreadCreationObject(int size) {
        if (threadCreationObject == null) {
            threadCreationObject = new ThreadCreation(size);
        }
        return threadCreationObject;
    }

    private ThreadCreation() {}

    private ThreadCreation(int size) {
        executorService = Executors.newFixedThreadPool(size);
    }

    public void createClientHelper(Socket clientSocket) {
        Runnable worker = new ClientHelper(clientSocket);
        ClientSocketHelper.addSocket(clientSocket);
        executorService.execute(worker);
    }

    public void createServerListener(ServerSocket server) {
        serverListenerThread = new ServerListener(server, this);
        executorService.execute(serverListenerThread);
    }

    public void createReportGeneration() {
        reportGenerationThread = new ReportGeneration();
        executorService.execute(reportGenerationThread);
    }

    public void createSaveNumbersToFile() {
        saveNumbersToFileThread = new SaveNumbersToFile();
        executorService.execute(saveNumbersToFileThread);
    }

    public void createConnectionDeniedHelper(Socket clientSocket) {
        Runnable connectionDeniedHelperThread = new ConnectionDeniedHelper(clientSocket);
        executorService.execute(connectionDeniedHelperThread);
    }

    /*
        this method terminates all the threads
     */
    public void terminateThreads() {

        ClientSocketHelper.terminateAllClientConnections();

        ((ReportGeneration) reportGenerationThread).terminate();

        ((ServerListener) serverListenerThread).close();

        ((SaveNumbersToFile) saveNumbersToFileThread).terminate();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        executorService.shutdownNow();
        System.out.println("Executer service terminated : " + executorService.isTerminated());
    }

}
