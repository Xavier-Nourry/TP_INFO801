package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Door implements Runnable {
    public static final String LOCKED = "Locked";
    protected static final String LOCKING = "Locking";
    private final String buildingID;
    public final String doorID;
    private final RemoteSpace ts;


    public Door(String buildingID, int doorNumber) {
        this.buildingID = buildingID;
        this.doorID = buildingID + " - Door" + doorNumber;

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(doorID);
    }

    public String getId() {
        return doorID;
    }

    @Override
    public void run() {
        // On lance le processus de gestion de passage
        new Thread(new CrossingManager(buildingID, doorID)).start();

        setInitialState();

        monitor();
    }

    private void setInitialState() {
        // Etat initial: la porte est verrouillée
        try {
            ts.put(this.doorID, LOCKED, true);
        } catch (InterruptedException e) {
            System.out.println(doorID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }

    private void monitor() {
        try {
            monitorLocking();
        } catch (InterruptedException e) {
            System.out.println(doorID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    // Gère le verrouillage/déverrouillage de la porte
    private void monitorLocking() throws InterruptedException {
        Object[] lockingAction = ts.get(
                new ActualField(doorID),
                new ActualField(LOCKING),
                new FormalField(Boolean.class)
        );
        Boolean mustLocked = (Boolean) lockingAction[2];

        Object[] blockedLockingAction = ts.queryp(
                new ActualField(buildingID),
                new ActualField(Building.DOOR_RELEASE)
        );
        boolean lockingAuthorization = blockedLockingAction == null;

        // On peut changer le statut de la porte si on cherche à déverrouiller, ou si on a le droit de verrouiller
        if (!mustLocked || lockingAuthorization)
                ts.put(doorID, LOCKED , mustLocked);
    }
}
