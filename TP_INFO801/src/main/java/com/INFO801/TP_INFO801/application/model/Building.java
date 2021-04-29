package com.INFO801.TP_INFO801.application.model;

public class Building{
    public String id;
    public Door[] doors;

    public Building(String id, int nbDoors){
        this.id = id;
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
}
