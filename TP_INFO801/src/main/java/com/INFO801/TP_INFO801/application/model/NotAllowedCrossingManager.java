package com.INFO801.TP_INFO801.application.model;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

import static com.INFO801.TP_INFO801.access_system.CrossingManager.UNAUTHORIZED_CROSSING;

public class NotAllowedCrossingManager implements Runnable {
    private final Door door;
    private final RemoteSpace server;

    public NotAllowedCrossingManager(Door door, RemoteSpace server) {
        this.door = door;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                notAllowedCrossingDetection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notAllowedCrossingDetection() throws InterruptedException {
        server.get(new ActualField(door.id), new ActualField(UNAUTHORIZED_CROSSING));
        door.setNotAllowedCross(true);
    }
}
