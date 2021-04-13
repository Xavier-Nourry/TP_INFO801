package com.INFO801.TP_INFO801.database_server;

import java.io.Serializable;

public class Building implements Serializable {
    public String id;
    public boolean isOnAlarm;

    public Building(String id){
        this.id = id;
        isOnAlarm = false;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                ", isOnAlarm=" + isOnAlarm +
                '}';
    }
}
