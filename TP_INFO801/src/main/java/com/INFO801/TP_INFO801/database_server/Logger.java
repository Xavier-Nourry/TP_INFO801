package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.Date;

public class Logger {
    private final ArrayList<LogEntry> logs;

    public Logger(){
        logs = new ArrayList<>();
    }

    public void logEntrance(Building b, Pass p, Date d){
        logs.add(new EnterExitLogEntry("ENTER", d, b, p));
    }

    public void logExit(Building b, Pass p, Date d){
        logs.add(new EnterExitLogEntry("EXIT", d, b, p));
    }

    public void logFireAlarm(Building b, Date d) {
        logs.add(new AlarmEntry("FIRE_ON", d, b));
    }

    public void logStopFireAlarm(Building b, Date d) {
        logs.add(new AlarmEntry("FIRE_OFF", d, b));
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
