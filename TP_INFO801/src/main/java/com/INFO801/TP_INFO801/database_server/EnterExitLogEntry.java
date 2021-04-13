package com.INFO801.TP_INFO801.database_server;

import java.util.Date;

public class EnterExitLogEntry extends LogEntry{
    protected final Building b;
    protected final Pass p;

    public EnterExitLogEntry(String type, Date d, Building b, Pass p) {
        super(type, d);
        this.b = b;
        this.p = p;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s, %s %s (%s)", type, b.id, p.firstName, p.lastName, d.toString());
    }
}
