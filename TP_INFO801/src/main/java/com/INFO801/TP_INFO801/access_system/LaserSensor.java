package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class LaserSensor implements Runnable {
    private static final String DETECTED_CROSSING = "detectedCrossing";
    private final String doorID;
    private final String sensorID;
    private final RemoteSpace ts;


    public LaserSensor(String doorID) {
        this.doorID = doorID;
        this.sensorID = doorID + " - Laser";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(sensorID);
    }

    @Override
    public void run() {
        monitor();
    }

    private void monitor() {
        try {
            monitorCrossingDetection();
        } catch (InterruptedException e) {
            System.out.println(sensorID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }

        monitor(); // S'appelle récursivement
    }

    private void monitorCrossingDetection() throws InterruptedException {
        ts.get(new ActualField(sensorID), new ActualField(DETECTED_CROSSING));
        ts.put(doorID, CrossingManager.CROSSING);
    }
}
