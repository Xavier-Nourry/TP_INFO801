package com.INFO801.TP_INFO801.application.model;

import org.jspace.ActualField;
import org.jspace.RemoteSpace;

import java.util.Observable;

public class Building extends Observable{
    public String id;
    // TODO : à décommenter
    //private final RemoteSpace server;
    public Door[] doors;

    public Building(int id, int nbDoors){
        this.id = "Building"+ id;
        // TODO : initialiser server avec methode de connexion
        this.doors = new Door[nbDoors];
        generateDoors(nbDoors);
    }

    private void generateDoors(int nbDoors) {
        for(int i = 0; i < nbDoors; i++){
            this.doors[i] = new Door(this.id, i+1);
        }
    }

    public void runAll(){
        for(int i = 0; i < doors.length; i++){
            new Thread(doors[i]).start();
        }
    }

    /* TODO : à décommenter
    public void notifyStartedFire(){
        try {
            server.put(new ActualField(id), new ActualField("Fire"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyStoppedFire(){
        try {
            server.put(new ActualField(id), new ActualField("FireEnd"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
