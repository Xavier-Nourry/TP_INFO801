package com.INFO801.TP_INFO801.application.model;

import com.INFO801.TP_INFO801.access_system.remoteConnections;
import org.jspace.RemoteSpace;

import java.util.Observable;

public class Building extends Observable{
    public String id;
    private final RemoteSpace server;
    public Door[] doors;

    public Building(String id, int nbDoors){
        this.id = id;
        this.server = remoteConnections.remoteSpaceConnexion(id);
        this.doors = new Door[nbDoors];
        generateDoors(nbDoors);
    }

    private void generateDoors(int nbDoors) {
        for(int i = 0; i < nbDoors; i++){
            this.doors[i] = new Door(this.id, i+1);
        }
    }

    public void runAll(){
        for (Door door : doors) {
            new Thread(door).start();
        }
    }

    public void notifyStartedFire(){
        try {
            server.put(id, "Fire");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyStoppedFire(){
        try {
            server.put(id, "FireEnd");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
