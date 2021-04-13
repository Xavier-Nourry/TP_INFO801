package com.INFO801.TP_INFO801.database_server;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    protected final String type;
    protected final String info;
    protected final Date d;

    public LogEntry(String type, String info, Date d) {
        this.type = type;
        this.info = info;
        this.d = d;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", type, info, d.toString());
    }
}