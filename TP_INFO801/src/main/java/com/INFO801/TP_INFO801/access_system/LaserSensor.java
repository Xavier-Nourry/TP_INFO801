package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

public class LaserSensor implements Runnable {
    private final String sensorName;
    private final String doorName;

    public LaserSensor(String doorName) {
        this.sensorName = doorName + " - Laser";
        this.doorName = doorName;
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(sensorName);
        assert ts != null;

        while (true){
            monitorCrossing(ts);
        }
    }

    private void monitorCrossing(RemoteSpace ts) {
        try {
            ts.get(new ActualField(sensorName));
            ts.put(doorName, CrossingManager.CROSSING);
        } catch (InterruptedException e) {
            System.out.println(sensorName + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
