package com.INFO801.TP_INFO801.access_system;

public class Building implements Runnable {
    private final String buildingName;
    private final int nbDoors;

    public Building(String buildingName, int nbDoors) {
        this.buildingName = buildingName;
        this.nbDoors = nbDoors;
    }

    @Override
    public void run() {
        Thread door;
        for (int i = 1; i <= nbDoors; i++) {
            door = new Thread(new Door(buildingName, i));
            door.start();
        }

        Thread authorizationsManager = new Thread((new AuthorizationManager(buildingName)));
        authorizationsManager.start();
    }
}
