package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.Date;

public class Logger {
    public final ArrayList<LogEntry> logs;

    public Logger(){
        logs = new ArrayList<>();
    }

    public void logEntrance(Building b, Pass p, Date d){
        logs.add(new LogEntry("ENTER", b.id+", "+p.firstName+" "+p.lastName ,d));
    }

    public void logExit(Building b, Pass p, Date d){
        logs.add(new LogEntry("EXIT", b.id+", "+p.firstName+" "+p.lastName, d));
    }

    public void logFireAlarm(Building b, Date d) {
        logs.add(new LogEntry("FIRE_ON", b.id ,d));
    }

    public void logStopFireAlarm(Building b, Date d) {
        logs.add(new LogEntry("FIRE_OFF", b.id ,d));
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for(LogEntry e : logs){
            b.append(e.toString()).append("\n");
        }
        return b.toString();
    }
}
