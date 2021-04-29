package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

abstract public class SwipeCardReader implements Runnable{
    private final String buildingID;
    private final String doorID;
    protected final String readerID;
    private final RemoteSpace ts;
    protected String direction;
    protected String greenLightID;

    protected SwipeCardReader(String buildingID, String doorID, String position) {
        this.readerID = doorID + " - " + position + " Reader";
        this.doorID = doorID;
        this.buildingID = buildingID;

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(readerID);
    }

    @Override
    public void run() {
        // On demander le lancement des processus de voyants
        startLightThreads();

        monitor();
    }

    protected abstract void startLightThreads();

    private void monitor() {
        try {
            monitorReading();
        } catch (InterruptedException e) {
            System.out.println(readerID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    private void monitorReading() throws InterruptedException {
        Object[] request = ts.get(
                new ActualField(readerID),
                new FormalField(String.class));
        String swipeCardId = (String) request[1];

        ts.put(buildingID, doorID, CrossingManager.CROSSING_REQUEST, direction, swipeCardId);
    }

    public String getGreenLightID() {
        return greenLightID;
    }
}
