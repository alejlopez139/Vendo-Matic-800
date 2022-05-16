package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Log {
    /*
        Creates log file if it doesn't exist
        prints into the log file, date and time AM/PM and then whatever message is passed into the method
        inserts a space between date and time and the message to log for easy use
     */
    private static final String LOGPATH = "vendingMachine.log";
    public static void logTransactions(String message){

        File logFile = new File(LOGPATH);
        DateFormat dtfmt = new SimpleDateFormat("MM/dd/yyyy hh.mm aa ");
        String dateString = dtfmt.format(new Date()).toString();
        try (PrintWriter log = new PrintWriter(new FileOutputStream(logFile, true))){
            log.println(dateString + message);
        }
        catch (FileNotFoundException e){
            System.out.println("***Unable to open log file: " + logFile.getAbsolutePath());
        }
    }
}
