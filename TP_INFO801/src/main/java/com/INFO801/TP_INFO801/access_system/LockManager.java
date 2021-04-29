package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockManager implements Runnable {
    private final String buildingID;
    private final String doorID;
    private final String managerID;
    private final RemoteSpace ts;
    private ExternalSwipeCardReader extSCR;
    private InternalSwipeCardReader inSCR;

    public LockManager(String buildingID, String doorID) {
        this.doorID = doorID;
        this.buildingID = buildingID;
        this.managerID = doorID + " - LockManager";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(managerID);
    }

    @Override
    public void run() {
        // On lance le processus de minuterie pour le verrouillage automatique
        LockTimer lockTimer = new LockTimer(doorID);
        lockTimer.setInitialState();
        new Thread(lockTimer).start();

        // On lance le processus de badgeuse extérieure
        extSCR = new ExternalSwipeCardReader(buildingID, doorID);
        new Thread(extSCR).start();

        // On lance le processus de badgeuse intérieure
        inSCR = new InternalSwipeCardReader(buildingID, doorID);
        new Thread(inSCR).start();

        monitor();
    }

    private void monitor() {
        try {
            monitorLocking();
        } catch (InterruptedException e) {
            System.out.println(managerID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    private void monitorLocking() throws InterruptedException {
        Object[] authorisation = ts.get(
                new ActualField(doorID),
                new ActualField(AuthorizationManager.OPENING_AUTHORIZATION),
                new FormalField(String.class),
                new FormalField(Boolean.class),
                new FormalField(String.class)
        );
        String direction = (String) authorisation[2];
        Boolean canOpen = (Boolean) authorisation[3];
        String swipeCardId = (String) authorisation[4];
        String lightID;

        if (canOpen){ // Autorisé à passer
            lightID = (direction.equals(CrossingManager.IN_DIRECTION))? extSCR.getGreenLightID() : inSCR.getGreenLightID();
            ts.put(lightID, Light.LIGHTING, Boolean.TRUE);
            ts.put(doorID, Door.LOCKING, Boolean.FALSE);
            ts.put(doorID, LockTimer.ACTIVATION, swipeCardId);
            Thread.sleep(5000);
            ts.put(lightID, Light.LIGHTING, Boolean.FALSE);
        }else{ // Pas autorisé à passer
            lightID = extSCR.getRedLightID();
            ts.put(lightID, Light.LIGHTING, Boolean.TRUE);
            Thread.sleep(10000);
            ts.put(lightID, Light.LIGHTING, Boolean.FALSE);
        }
    }
}
