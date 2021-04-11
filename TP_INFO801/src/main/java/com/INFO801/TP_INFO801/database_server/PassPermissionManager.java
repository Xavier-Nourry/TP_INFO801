package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.HashMap;

public class PassPermissionManager {
    public ArrayList<Building> buildings;
    public ArrayList<Pass> passes;
    private HashMap<String, ArrayList<Building>> permissions;

    public PassPermissionManager(){
        buildings = new ArrayList<>();
        passes = new ArrayList<>();
        permissions = new HashMap<>();
    }

    public void createBuilding(String buildingId) {
        //avoid duplicates
        if(buildings.stream().anyMatch(building -> building.id.equals(buildingId))) return;
        Building b = new Building(buildingId);
        buildings.add(b);
    }
}
