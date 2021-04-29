package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Door implements Runnable {
    public static final String CROSSING = "Crossing";
    public static final String LOCKING = "Locking";
    public static final String CROSSING_REQUEST = "CrossingRequest";
    private final String buildingName;
    private final String doorName;

    public Door(String buildingName, int doorNumber) {
        this.buildingName = buildingName;
        this.doorName = buildingName + " - Door" + doorNumber;
    }

    @Override
    public void run() {
        Thread laserSensor = new Thread(new LaserSensor(doorName));
        laserSensor.start();

        Thread externalSwipeCardReader = new Thread(new ExternalSwipeCardReader(buildingName, doorName));
        externalSwipeCardReader.start();
        Thread internalSwipeCardReader = new Thread(new InternalSwipeCardReader(buildingName, doorName));
        internalSwipeCardReader.start();

        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(doorName);
        assert ts != null;

        // Initial state: the door is locked
        try {
            ts.put(this.doorName, true);
        } catch (InterruptedException e) {
            System.out.println(doorName + " : erreur while communicating with the tuple space");
            e.printStackTrace();
            return;
        }

        while (true){
            this.monitorLocking(ts);
        }
    }

    private void monitorLocking(RemoteSpace ts) {
        try {
            Object[] locked = ts.get(new ActualField(doorName), new ActualField(LOCKING), new FormalField(Boolean.class));
            ts.put(doorName, locked[2]);
        } catch (InterruptedException e) {
            System.out.println(doorName + " : erreur while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
