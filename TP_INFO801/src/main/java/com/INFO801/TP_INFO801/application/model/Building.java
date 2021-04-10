package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Building{
    private int id;
    private ArrayList<Door> doors;

    public Building(int id, int nbDoors){
        this.id = id;
        this.doors = generateDoors(nbDoors);
    }

    private ArrayList<Door> generateDoors(int nbDoors) {
        ArrayList<Door> res = new ArrayList<Door>();
        for(int i = 0; i < nbDoors; i++){
            res.add(new Door(i));
        }
        return res;
    }

    public void runAll(){
        this.doors.forEach((door) -> door.run());
    }
}
