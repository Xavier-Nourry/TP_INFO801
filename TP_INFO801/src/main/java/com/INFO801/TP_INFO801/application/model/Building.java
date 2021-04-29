package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Building{
    public String id;
    public Door[] doors;

    public Building(int id, int nbDoors){
        this.id = "Building"+ id;
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
}
