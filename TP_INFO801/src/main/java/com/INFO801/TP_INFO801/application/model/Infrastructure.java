package com.INFO801.TP_INFO801.application.model;


public class Infrastructure {
    public Building[] buildings;

    public Infrastructure(int nbBuildings, int nbDoorsPerBuilding){
        this.buildings = new Building[nbBuildings];
        generateBuildings(nbBuildings, nbDoorsPerBuilding);
    }

    private void generateBuildings(int nbBuildings, int nbDoorsPerBuilding){
        for(int i = 0; i < nbBuildings; i++){
            this.buildings[i] = (new Building(i+1, nbDoorsPerBuilding));
        }
    }

    public void runAll(){
        for(int i = 0; i < buildings.length; i++){
            buildings[i].runAll();
        }
    }
}