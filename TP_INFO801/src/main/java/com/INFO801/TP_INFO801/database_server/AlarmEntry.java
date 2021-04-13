package com.INFO801.TP_INFO801.database_server;

import java.util.Date;

public class AlarmEntry extends LogEntry{

    protected final Building b;

    public AlarmEntry(String type, Date d, Building b) {
        super(type, d);
        this.b = b;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", type, b.id, d.toString());
    }
}
