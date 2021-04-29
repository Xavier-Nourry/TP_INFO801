package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class Light implements Runnable {
    public static final String GREEN = "Green";
    public static final String RED = "Red";
    public static final String LIGHTING = "Lighting";

    public final String lightName;

    public Light(String readerName, String color) {
        this.lightName = readerName + " - " + color + " Light";
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(lightName);
        assert ts != null;

        while (true){
            monitorLighting(ts);
        }
    }

    private void monitorLighting(RemoteSpace ts) {
        try {
            Object[] lighting = ts.get(new ActualField(lightName), new ActualField(LIGHTING), new FormalField(boolean.class));
            ts.put(lightName, lighting[2]);
        } catch (InterruptedException e) {
            System.out.println(lightName + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }

    public String getLightID() {
        return lightName;
    }
}
