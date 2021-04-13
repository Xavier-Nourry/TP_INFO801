package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireManager implements Runnable {
    public static final String FIRE_ALARM_ON = "FireAlarmOn";
    public static final String FIRE_ALARM_OFF = "FireAlarmOff";
    public static final String DOOR_RELEASE = "DoorRelease";
    public static final String ALL_DOORS_LOCKED = "allDoorsLocked";
    public static final String FIRE_END = "FireEnd";
    private final String buildingName;

    public FireManager(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(buildingName);
        assert ts != null;
        while (true){
            this.monitorFireManagement(ts);
        }
    }

    private void monitorFireManagement(RemoteSpace ts) {
        try {
            ts.get(new ActualField(buildingName), new ActualField(FireDetector.FIRE_DETECTED));
            ts.put(buildingName, FIRE_ALARM_ON);
            ts.put(buildingName, DOOR_RELEASE);
            ts.put(buildingName, ALL_DOORS_LOCKED, false);
            //TODO: ajouter la communication avec la bdd
            ts.get(new ActualField(buildingName), new ActualField(FIRE_END));
            ts.get(new ActualField(buildingName), new ActualField(DOOR_RELEASE));
            ts.put(buildingName, ALL_DOORS_LOCKED, true);
            ts.put(buildingName, FIRE_ALARM_OFF);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
