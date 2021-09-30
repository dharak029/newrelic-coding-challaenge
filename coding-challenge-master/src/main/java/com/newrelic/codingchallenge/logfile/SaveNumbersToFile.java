package com.newrelic.codingchallenge.logfile;

import com.newrelic.codingchallenge.numberprocessing.CheckDuplicate;

public class SaveNumbersToFile implements Runnable{

    private int saveNumberFrequency = 1500;
    private boolean run = true;


    @Override
    public void run() {
        FileHelper fileHandler = FileHelper.getFileHelperObject();
        CheckDuplicate processor = CheckDuplicate.getCheckDuplicateObject();
        try {
            while (run) {
                Thread.sleep(saveNumberFrequency);
                StringBuffer stringBuffer = processor.getStringBuffer();
                if (stringBuffer.length() != 0) {
                    fileHandler.addToFile(stringBuffer.toString());
                    processor.emptyBuffer();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        run = false;
    }
}
