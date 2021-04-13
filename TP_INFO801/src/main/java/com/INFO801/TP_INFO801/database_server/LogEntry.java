package com.INFO801.TP_INFO801.database_server;

import java.io.Serializable;
import java.util.Date;

public abstract class LogEntry implements Serializable {
    protected final String type;
    protected final Date d;

    public LogEntry(String type, Date d) {
        this.type = type;
        this.d = d;
    }

    @Override
    public String toString() {
        return String.format("[%s](%s)", type, d.toString());
    }
}