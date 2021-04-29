package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.util.ArrayList;

public class Building implements Runnable {
    protected static final String ALL_DOORS_LOCKED = "allDoorsLocked";
    protected static final String DOOR_RELEASE = "DoorRelease";
    public static final String BUILDING_CREATION = "BuildingCreation";

    private final String buildingID;
    private final ArrayList<Door> doors;
    private final RemoteSpace ts;

    public Building(com.INFO801.TP_INFO801.database_server.Building dbBuilding) {
        buildingID = dbBuilding.id;
        doors = new ArrayList<>();
        // On crée toutes les portes du bâtiment
        for (int nbDoor = 1; nbDoor <= dbBuilding.nbDoors; nbDoor++)
            doors.add(new Door(buildingID, nbDoor));

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(buildingID);

        // Le building préviens de sa création pour permettre à l'application de se synchroniser
        try {
            ts.put(BUILDING_CREATION, buildingID, doors.size());
        } catch (InterruptedException e) {
            System.out.println(buildingID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        // On lance les processus des gestion des portes
        for (Door door: doors)
            new Thread(door).start();

        // On lance le processus de gestion d'incendie
        new Thread(new FireManager(buildingID)).start();

        // On lance le processus de gestion des autorisations
        new Thread(new AuthorizationManager(buildingID)).start();

        monitor();
    }

    private void monitor() {
        try {
            monitorDoorsManagement();
        } catch (InterruptedException e) {
            System.out.println(buildingID + " : error while communicating with the tuple space");
            e.printStackTrace();
            return;
        }

        monitor(); // S'appelle récursivement
    }

    // Gestion de l'ensemble des portes du bâtiment
    private void monitorDoorsManagement() throws InterruptedException {
        Object[] action = ts.get(
                new ActualField(buildingID),
                new ActualField(ALL_DOORS_LOCKED),
                new FormalField(Boolean.class)
        );

        Boolean doorsLocked = (Boolean) action[2];
        for (Door door: doors)
            ts.put(door.getId(), Door.LOCKING, doorsLocked);
    }
}
