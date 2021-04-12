package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.Date;

public class EntryLogger {
    private final ArrayList<LogEntry> logs;

    public EntryLogger(){
        logs = new ArrayList<>();
    }

    public void logEntrance(Building b, Pass p, Date d){
        logs.add(new LogEntry("Entrance", b, p, d));
    }

    public void logExit(Building b, Pass p, Date d){
        logs.add(new LogEntry("Exit", b, p, d));
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for(LogEntry e : logs){
            b.append(e.toString()).append("\n");
        }
        return b.toString();
    }

    private static class LogEntry {
        private final String type;
        private final Building b;
        private final Pass p;
        private final Date d;

        public LogEntry(String type, Building b, Pass p, Date d) {
            this.type = type;
            this.b = b;
            this.p = p;
            this.d = d;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s, %s %s (%s)", type, b.id, p.firstName, p.lastName, d.toString());
        }
    }
}
