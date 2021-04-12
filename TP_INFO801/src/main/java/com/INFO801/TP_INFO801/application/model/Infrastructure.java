package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Infrastructure {
    private ArrayList<Building> buildings;

    public Infrastructure(int nbBuildings, int nbDoorsPerBuilding){
        this.buildings = generateBuildings(nbBuildings, nbDoorsPerBuilding);
    }

    private ArrayList<Building> generateBuildings(int nbBuildings, int nbDoorsPerBuilding){
        ArrayList<Building> res = new ArrayList<Building>();
        for(int i = 0; i < nbBuildings; i++){
            res.add(new Building(i+1, nbDoorsPerBuilding));
        }
        return res;
    }

    public void runAll(){
        this.buildings.forEach((building -> building.runAll()));
    }
}