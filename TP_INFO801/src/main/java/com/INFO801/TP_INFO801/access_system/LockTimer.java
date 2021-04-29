package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockTimer implements Runnable {
    public static final String ACTIVATION = "ActiveLockTimer";
    private final String doorID;
    private final String timerID;
    private final RemoteSpace ts;

    public LockTimer(String doorID) {
        this.doorID = doorID;
        this.timerID = doorID + " - LockTimer";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(timerID);
    }

    public LockTimer(LockTimer lockTimer) {
        this.doorID = lockTimer.doorID;
        this.timerID = lockTimer.timerID;
        this.ts = lockTimer.ts;
    }

    @Override
    public void run() {
        monitor();
    }

    public void setInitialState() {
        try { // Au départ, le compteur du timer est à 0
            ts.put(timerID, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void monitor() {
        try {
            monitorTimer();
        } catch (InterruptedException e) {
            System.out.println(timerID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
        monitor();
    }

    private void monitorTimer() throws InterruptedException {
        Object[] activation = ts.get(
                new ActualField(doorID),
                new ActualField(ACTIVATION),
                new FormalField(String.class));
        String swipeCardId = (String) activation[2];

        // Appel récursif pour le prochain timer
        new Thread(new LockTimer(this)).start();

        Object[] timerCounter = ts.get(
                new ActualField(timerID),
                new FormalField(Integer.class));
        Integer counter = (Integer) timerCounter[1];
        // On incrémente le compteur de timers actif pour la porte
        ts.put(timerID, counter+1);

        Thread.sleep(30000);

        Object[] AuthorizedCrosser = ts.getp(
                    new ActualField(doorID),
                    new ActualField(AuthorizationManager.AUTHORIZED_CROSSER),
                    new FormalField(String.class),
                    new ActualField(swipeCardId));

        if (AuthorizedCrosser != null){
            // S'il reste quelqu'un d'autorisé à passer, on vérifie qu'il y a encore des timers en cours
            // Si non il faut reverrouiller par sécurité
            timerCounter = ts.get(
                    new ActualField(timerID),
                    new FormalField(Integer.class));
            counter = (Integer) timerCounter[1];

            int updatedCounter = counter - 1;
            ts.put(timerID, updatedCounter);
            if (updatedCounter == 0){
                ts.put(doorID, Door.LOCKING, Boolean.TRUE);
            }
        }
    }
}
