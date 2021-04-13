package com.INFO801.TP_INFO801.database_server;

import java.io.Serializable;

public class Building implements Serializable {
    public String id;

    public Building(String id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                '}';
    }
}
