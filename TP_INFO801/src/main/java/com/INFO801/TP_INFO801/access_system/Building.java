package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.util.ArrayList;

public class Building implements Runnable {
    private final String buildingName;
    private final int nbDoors;

    private final ArrayList<String> doorNames;

    public Building(String buildingName, int nbDoors) {
        doorNames = new ArrayList<>();
        this.buildingName = buildingName;
        this.nbDoors = nbDoors;
    }

    @Override
    public void run() {
        Door door;
        for (int i = 1; i <= nbDoors; i++) {
            door = new Door(buildingName, i);
            doorNames.add(door.doorName);
            new Thread(door).start();
        }

        new Thread(new FireDetector(buildingName)).start();
        new Thread(new FireManager(buildingName)).start();
        new Thread(new FireAlarm(buildingName)).start();

        Thread authorizationsManager = new Thread((new AuthorizationManager(buildingName)));
        authorizationsManager.start();
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(buildingName);
        assert ts != null;

        while (true){
            this.monitorDoorsManagement(ts);
        }
    }

    private void monitorDoorsManagement(RemoteSpace ts) {
        try {
            Object[] doorsAction = ts.get(new ActualField(buildingName), new ActualField(FireManager.ALL_DOORS_LOCKED), new FormalField(boolean.class));
            boolean locked = (boolean) doorsAction[2];
            for (String doorName : doorNames) {
                ts.put(doorName, (Door.LOCKING), locked);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
