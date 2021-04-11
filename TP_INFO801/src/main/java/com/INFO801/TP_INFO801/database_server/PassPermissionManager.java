package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void createPass(String passId, String firstName, String lastName, String[] authorizedBuildingIds) {
        Pass p = new Pass(passId, firstName, lastName);
        ArrayList<Building> authorizedBuildings = new ArrayList<>();
        for(Building b : buildings){
            if(Arrays.stream(authorizedBuildingIds).anyMatch(bId -> bId.equals(b.id))){
                authorizedBuildings.add(b);
            }
        }
        passes.add(p);
        permissions.put(p.id, authorizedBuildings);
    }

    public void deletePass(String passId) {
        permissions.remove(passId);
        passes.removeIf(pass -> pass.id.equals(passId));
    }
}
