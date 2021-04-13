package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireAlarm implements Runnable {
    private static final String FIRE_ALARM = "FireAlarm";
    private final String buildingName;

    public FireAlarm(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(buildingName);
        assert ts != null;
        while (true){
            this.monitorAlarm(ts);
        }
    }

    private void monitorAlarm(RemoteSpace ts) {
        try {
            ts.get(new ActualField(buildingName), new ActualField(FireManager.FIRE_ALARM_ON));
            ts.put(buildingName, FIRE_ALARM);
            ts.get(new ActualField(buildingName), new ActualField(FireManager.FIRE_ALARM_OFF));
            ts.get(new ActualField(buildingName), new ActualField(FIRE_ALARM));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
