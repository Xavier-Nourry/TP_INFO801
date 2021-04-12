package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Infrastructure {
    private String tsServerURI;
    private ArrayList<Building> buildings;

    public Infrastructure(int nbBuildings, int nbDoorsPerBuilding, String tsServerURI){
        this.tsServerURI = tsServerURI;
        this.buildings = generateBuildings(nbBuildings, nbDoorsPerBuilding);
    }

    private ArrayList<Building> generateBuildings(int nbBuildings, int nbDoorsPerBuilding){
        ArrayList<Building> res = new ArrayList<Building>();
        for(int i = 0; i < nbBuildings; i++){
            res.add(new Building(i, nbDoorsPerBuilding, tsServerURI));
        }
        return res;
    }

    // TODO : voir où et quand join les threads
    public void runAll(){
        this.buildings.forEach((building -> building.runAll()));
    }
}