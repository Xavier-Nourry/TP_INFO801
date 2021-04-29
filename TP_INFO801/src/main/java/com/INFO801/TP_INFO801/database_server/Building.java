package com.INFO801.TP_INFO801.database_server;

import java.io.Serializable;

public class Building implements Serializable {
    public String id;
    public int nbDoors;
    public boolean isOnAlarm;

    public Building(String id, int nbDoors){
        this.id = id;
        isOnAlarm = false;
        this.nbDoors = nbDoors;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                ", nbDoors=" + nbDoors +
                ", isOnAlarm=" + isOnAlarm +
                '}';
    }
}
