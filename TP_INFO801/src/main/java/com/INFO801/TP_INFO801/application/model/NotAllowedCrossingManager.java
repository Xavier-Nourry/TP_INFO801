package com.INFO801.TP_INFO801.application.model;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class NotAllowedCrossingManager implements Runnable {
    private final Door door;
    private final RemoteSpace server;

    public NotAllowedCrossingManager(Door door, RemoteSpace server) {
        this.door = door;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            notAllowedCrossingDetection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notAllowedCrossingDetection() throws InterruptedException {
        Object[] response;
        response = server.get(new ActualField(door.id), new ActualField("UnauthorizedCrossing"), new FormalField(Boolean.class));
        if(response != null){
            door.setNotAllowedCross((Boolean) response[1]);
        }
    }
}
