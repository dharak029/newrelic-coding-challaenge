package com.newrelic.codingchallenge.logfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    private static FileHelper fileHelper = null;

    private final String filename = "./src/main/java/com/newrelic/codingchallenge/logfile/numbers.log";

    private FileHelper() {
        deleteFile();
    }

    public static FileHelper getFileHelperObject() {
        if (fileHelper == null) {
            fileHelper = new FileHelper();
        }
        return fileHelper;
    }

    /*
        this method will be used to delete previous log file when user starts server again
     */
    private void deleteFile() {
        File file = new File(filename);
        file.delete();
    }

    /*
        this method is used to add numbers to file
     */
    public void addToFile(String str) {
        File file = new File(filename);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
