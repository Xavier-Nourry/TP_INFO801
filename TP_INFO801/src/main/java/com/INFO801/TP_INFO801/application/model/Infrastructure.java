package com.INFO801.TP_INFO801.application.model;

import java.util.ArrayList;

public class Infrastructure {
    private ArrayList<Building> buildings;
    private ArrayList<Pass> passes;

    public Infrastructure(int nbBuildings, int nbDoorsPerBuilding, int nbPasses){
        this.buildings = generateBuildings(nbBuildings, nbDoorsPerBuilding);
        this.passes = generatePasses(nbPasses);
    }

    private ArrayList<Building> generateBuildings(int nbBuildings, int nbDoorsPerBuilding){
        ArrayList<Building> res = new ArrayList<Building>();
        for(int i = 0; i < nbBuildings; i++){
            res.add(new Building(i, nbDoorsPerBuilding));
        }
        return res;
    }

    private ArrayList<Pass> generatePasses(int nbPasses){
        ArrayList<Pass> res = new ArrayList<Pass>();
        for(int i = 0; i < nbPasses; i++){
            res.add(new Pass(i));
        }
        return res;
    }

    public void runAll(){
        this.buildings.forEach((building -> building.runAll()));
    }
}