package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireAlarm implements Runnable {
    private static final String FIRE_ALARM = "FireAlarm";
    public static final String FIRE_ALARM_ON = "FireAlarmOn";
    public static final String FIRE_ALARM_OFF = "FireAlarmOff";

    private final String buildingID;
    private final String alarmID;
    private final RemoteSpace ts;

    public FireAlarm(String buildingID) {
        this.buildingID = buildingID;
        this.alarmID = buildingID + " - FireAlarm";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(alarmID);
    }

    @Override
    public void run() {
        this.monitor();
    }

    private void monitor() {
        try {
            monitorAlarm();
        } catch (InterruptedException e) {
            System.out.println(alarmID + " : error while communicating with the tuple space");
            e.printStackTrace();
            return;
        }

        monitor(); // S'appelle récursivement
    }

    private void monitorAlarm() throws InterruptedException {
        ts.get(new ActualField(buildingID), new ActualField(FIRE_ALARM_ON));
        ts.put(buildingID, FIRE_ALARM);
        ts.get(new ActualField(buildingID), new ActualField(FIRE_ALARM_OFF));
        ts.get(new ActualField(buildingID), new ActualField(FIRE_ALARM));
    }
}
