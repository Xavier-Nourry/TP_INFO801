package com.INFO801.TP_INFO801.database_server;

public class Building {
    public String id;
    public int nbDoors;

    public Building(String id, int nbDoors){
        this.id = id;
        this.nbDoors = nbDoors;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                ", nbDoors=" + nbDoors +
                '}';
    }
}
