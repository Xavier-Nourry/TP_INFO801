package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Light implements Runnable {
    public static final String GREEN = "Green";
    public static final String RED = "Red";
    public static final String LIGHTING = "Lighting";

    public final String lightID;
    private final RemoteSpace ts;

    public Light(String readerID, String color) {
        this.lightID = readerID + " - " + color + " Light";

        // Connexion à l'espace de tuple
        ts = remoteConnections.remoteSpaceConnexion(lightID);
    }

    public String getLightID() {
        return lightID;
    }

    @Override
    public void run() {
        monitor();
    }

    private void monitor() {
        try {
            monitorLighting();
        } catch (InterruptedException e) {
            System.out.println(lightID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    private void monitorLighting() throws InterruptedException {
        Object[] lighting = ts.get(
                new ActualField(lightID),
                new ActualField(LIGHTING),
                new FormalField(Boolean.class));
        Boolean mustLight = (Boolean) lighting[2];

        ts.put(lightID, mustLight);
    }
}
