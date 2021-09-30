package com.newrelic.codingchallenge.numberprocessing;

import com.newrelic.codingchallenge.logfile.FileHelper;
import com.newrelic.codingchallenge.report.ReportGeneration;

public class CheckDuplicate {

    private static CheckDuplicate checkDuplicateObject = null;

    private StringBuffer stringBuffer;

    private CheckDuplicate() {
        FileHelper.getFileHelperObject();
        stringBuffer = new StringBuffer();
    }

    public static CheckDuplicate getCheckDuplicateObject() {
        if (checkDuplicateObject == null) {
            checkDuplicateObject = new CheckDuplicate();
        }
        return checkDuplicateObject;
    }

    /*
        this method checks duplicate number. If the number is already present in cache it  marks it as duplicate.
     */
    public void isDuplicate(String data) {

        boolean newData = Cache.getCacheObject().addNumber(data);

        if(newData) {
            ReportGeneration.incrementUniqueNumberCount();
            ReportGeneration.incrementUniqueTotalNumberCount();

            addToBuffer(data);
        }
        else {
            ReportGeneration.incrementDuplicateNumberCount();
        }
    }

    public void addToBuffer(String str) {
        stringBuffer.append(str);
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }

    public void emptyBuffer() {
        stringBuffer = new StringBuffer();
    }

}
