package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockManager implements Runnable {
    private final String buildingID;
    private final String doorID;
    private final String managerID;
    private final RemoteSpace ts;
    private String redExternalLightID;
    private String greenExternalLightID;
    private String greenInternalLightID;

    public LockManager(String buildingID, String doorID) {
        this.doorID = doorID;
        this.buildingID = buildingID;
        this.managerID = doorID + " - LockManager";

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(managerID);
    }

    @Override
    public void run() {
        // On lance le processus de minuterie pour le verrouillage automatique
        new Thread(new LockTimer(doorID)).start();

        // On lance le processus de badgeuse extérieure
        ExternalSwipeCardReader extSCR = new ExternalSwipeCardReader(buildingID, doorID);
        new Thread(extSCR).start();

        // On lance le processus de badgeuse intérieure
        InternalSwipeCardReader inSCR = new InternalSwipeCardReader(buildingID, doorID);
        new Thread(inSCR).start();

        // On garde les id des voyants pour pouvoir les gérer ensuite
        redExternalLightID = extSCR.getRedLightID();
        greenExternalLightID = extSCR.getGreenLightID();
        greenInternalLightID = inSCR.getGreenLightID();

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

        if (canOpen){ // Autorisé à passer
            String lightID = (direction.equals(CrossingManager.IN_DIRECTION))? greenExternalLightID : greenInternalLightID;
            ts.put(lightID, Light.LIGHTING, true);
            ts.put(doorID, Door.LOCKING, false);
            ts.put(doorID, LockTimer.ACTIVATION, swipeCardId);
            Thread.sleep(5000);
            ts.put(lightID, Light.LIGHTING, false);
        }else{ // Pas autorisé à passer
            ts.put(redExternalLightID, Light.LIGHTING, true);
            Thread.sleep(10000);
            ts.put(redExternalLightID, Light.LIGHTING, false);
        }
    }
}
