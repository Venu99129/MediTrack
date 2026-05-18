package org.airtribe.exceptions;

public class CSVFilesException extends Exception {
    StackTraceElement[] stackTrace;
    public CSVFilesException(String message, StackTraceElement[] stackTrace) {
         super(message);
         this.stackTrace = stackTrace;
    }

    public void printStackTrace() {
        System.err.println(getMessage());
        for (StackTraceElement element : stackTrace) {
            System.err.println("\tat " + element);
        }
    }

}
