package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Building{
    private final String id;
    private final ArrayList<Door> doors;

    public Building(int id, int nbDoors){
        this.id = "Building"+ id;
        this.doors = generateDoors(nbDoors);
    }

    private ArrayList<Door> generateDoors(int nbDoors) {
        ArrayList<Door> res = new ArrayList<>();
        for(int i = 0; i < nbDoors; i++){
            res.add(new Door(id, i+1));
        }
        return res;
    }

    public void runAll(){
        this.doors.forEach((door) -> new Thread(door).start());
    }
}
