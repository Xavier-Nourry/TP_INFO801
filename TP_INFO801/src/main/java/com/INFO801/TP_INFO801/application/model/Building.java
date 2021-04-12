package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Building{
    private String tsServerURI;
    private String id;
    private ArrayList<Door> doors;

    public Building(int id, int nbDoors, String tsServerURI){
        this.tsServerURI = tsServerURI;
        this.id = "Building"+ id;
        this.doors = generateDoors(nbDoors);
    }

    private ArrayList<Door> generateDoors(int nbDoors) {
        ArrayList<Door> res = new ArrayList<Door>();
        for(int i = 0; i < nbDoors; i++){
            res.add(new Door(id, i, tsServerURI));
        }
        return res;
    }

    public void runAll(){
        this.doors.forEach((door) -> new Thread(door).start());
    }
}
