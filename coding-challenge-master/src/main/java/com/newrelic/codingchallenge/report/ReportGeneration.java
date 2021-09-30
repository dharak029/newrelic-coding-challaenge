package com.newrelic.codingchallenge.report;

public class ReportGeneration implements Runnable{

    private static int uniqueNumberCount;
    private static int duplicateNumberCount;
    private static int uniqueTotalNumberCount;

    private boolean run = true;

    /*
        this method prints report on console at 10 seconds
     */
    @Override
    public void run() {
        while (run) {
            try {
                String fs = String.format("Received %d unique numbers, %d duplicates. Unique total: %d",
                        uniqueNumberCount, duplicateNumberCount, uniqueTotalNumberCount);
                System.out.println("\n" + fs);
                resetCounters();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }

    private void resetCounters() {
        uniqueNumberCount = 0;
        duplicateNumberCount = 0;
    }

    public void terminate() {
        run = false;
    }

    public static void incrementUniqueNumberCount() {
        uniqueNumberCount++;
    }

    public static void incrementDuplicateNumberCount() {
        duplicateNumberCount++;
    }

    public static void incrementUniqueTotalNumberCount() {
        uniqueTotalNumberCount++;
    }
}
