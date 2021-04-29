package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

abstract public class SwipeCardReader implements Runnable{
    public String greenLightName;

    protected String direction;
    protected final String readerName;
    private final String doorName;
    private final String buildingName;

    protected SwipeCardReader(String buildingName, String doorName, String position) {
        this.readerName = doorName + " - " + position + " Reader";
        this.doorName = doorName;
        this.buildingName = buildingName;
    }


    @Override
    public void run() {
        startLights();

        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(readerName);
        assert ts != null;

        while (true){
            this.monitorReading(ts);
        }
    }

    protected abstract void startLights();

    private void monitorReading(RemoteSpace ts) {
        try {
            Object[] request = ts.get(new ActualField(readerName), new FormalField(String.class));
            ts.put(buildingName, doorName, CrossingManager.CROSSING_REQUEST, direction, request[1]);
        } catch (InterruptedException e) {
            System.out.println(readerName + " : erreur while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
