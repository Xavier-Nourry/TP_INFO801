package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class FireDetector implements Runnable {
    public static final String FIRE = "Fire";
    public static final String FIRE_DETECTED = "FireDetected";
    private final String buildingName;

    public FireDetector(String buildingName) {
        this.buildingName = buildingName;
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(buildingName);
        assert ts != null;
        while (true){
            this.monitorDetection(ts);
        }
    }

    private void monitorDetection(RemoteSpace ts) {
        try {
            ts.get(new ActualField(buildingName), new ActualField(FIRE));
            ts.put(buildingName, FIRE_DETECTED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
