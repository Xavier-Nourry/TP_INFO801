package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireDetector implements Runnable {
    public static final String FIRE = "Fire";
    public static final String FIRE_DETECTED = "FireDetected";

    private final String buildingID;
    private final String detectorID;

    private final RemoteSpace ts;


    public FireDetector(String buildingID) {
        this.buildingID = buildingID;
        this.detectorID = buildingID + " - FireDetector";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(detectorID);
    }

    @Override
    public void run() {
        monitor();
    }

    private void monitor() {
        try {
            monitorDetection();
        } catch (InterruptedException e) {
            System.out.println(detectorID + " : error while communicating with the tuple space");
            e.printStackTrace();
            return;
        }

        monitor(); // S'appelle récursivement
    }

    private void monitorDetection() throws InterruptedException {
        ts.get(new ActualField(buildingID), new ActualField(FIRE));
        ts.put(buildingID, FIRE_DETECTED);
    }
}
