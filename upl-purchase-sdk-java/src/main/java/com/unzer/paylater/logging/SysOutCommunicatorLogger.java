package com.unzer.paylater.logging;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * A communicator logger that prints its message to {@link System#out}.
 * It includes a timestamp in yyyy-MM-ddTHH:mm:ss format in the system time zone.
 */
public final class SysOutCommunicatorLogger implements CommunicatorLogger {

    public static final SysOutCommunicatorLogger INSTANCE = new SysOutCommunicatorLogger();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .appendLiteral('T')
            .appendPattern("HH:mm:ss ").toFormatter();

    private SysOutCommunicatorLogger() {}

    @Override
    public void log(String message) {
        // System.out can be changed using System.setOut; make sure the same object is used for locking and printing
        final PrintStream sysOut = System.out;
        synchronized (sysOut) {
            sysOut.println(getDatePrefix() + message);
        }
    }

    @Override
    public void log(String message, Throwable thrown) {
        // System.out can be changed using System.setOut; make sure the same object is used for locking and printing
        final PrintStream sysOut = System.out;
        synchronized (sysOut) {
            sysOut.println(getDatePrefix() + message);
            if (thrown != null) {
                thrown.printStackTrace(sysOut);
            }
        }
    }

    private String getDatePrefix() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}
