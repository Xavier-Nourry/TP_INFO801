package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireManager implements Runnable {
    public static final String FIRE_ALARM_ON = "FireAlarmOn";
    public static final String FIRE_ALARM_OFF = "FireAlarmOff";
    public static final String FIRE_END = "FireEnd";

    private final String buildingID;
    private final String managerID;
    private final RemoteSpace ts;

    public FireManager(String buildingID) {
        this.buildingID = buildingID;
        this.managerID = buildingID + " - FireManager";

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(managerID);
    }

    @Override
    public void run() {
        // On lance le processus de détection d'incendie
        new Thread(new FireDetector(buildingID)).start();

        // On lance le processus d'alarme à incendie
        new Thread(new FireAlarm(buildingID)).start();

        monitor();
    }

    private void monitor() {
        try {
            monitorFireManagement();
        } catch (InterruptedException e) {
            System.out.println(managerID + " : error while communicating with the tuple space");
            e.printStackTrace();
            return;
        }

        monitor(); // S'appelle récursivement
    }

    private void monitorFireManagement() throws InterruptedException {
        ts.get(new ActualField(buildingID),
                new ActualField(FireDetector.FIRE_DETECTED));

        ts.put(buildingID, FIRE_ALARM_ON);
        ts.put(buildingID, Building.DOOR_RELEASE);
        ts.put(buildingID, Building.ALL_DOORS_LOCKED, Boolean.FALSE);

        //TODO: ajouter la communication avec la bdd

        ts.get(new ActualField(buildingID),
                new ActualField(FIRE_END));
        ts.get(
                new ActualField(buildingID),
                new ActualField(Building.DOOR_RELEASE));

        //TODO: ajouter la communication avec la bdd

        ts.put(buildingID, Building.ALL_DOORS_LOCKED, Boolean.TRUE);
        ts.put(buildingID, FIRE_ALARM_OFF);
    }
}
