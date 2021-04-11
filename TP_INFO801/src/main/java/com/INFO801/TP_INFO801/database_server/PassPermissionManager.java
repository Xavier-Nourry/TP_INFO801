package com.INFO801.TP_INFO801.database_server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class PassPermissionManager {
    public ArrayList<Building> buildings;
    public ArrayList<Pass> passes;
    private final HashMap<String, ArrayList<Building>> permissions;
    private final HashMap<Building, ArrayList<Pass>> peopleCurrentlyIn;

    public PassPermissionManager(){
        buildings = new ArrayList<>();
        passes = new ArrayList<>();
        permissions = new HashMap<>();
        peopleCurrentlyIn = new HashMap<>();
    }

    public void createBuilding(String buildingId) {
        //avoid duplicates
        if(buildings.stream().anyMatch(building -> building.id.equals(buildingId))) return;
        Building b = new Building(buildingId);
        buildings.add(b);
        peopleCurrentlyIn.put(b, new ArrayList<>());
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
        //Update permissions
        permissions.remove(passId);
        //Update list of people in buildings
        for(ArrayList<Pass> list : peopleCurrentlyIn.values()){
            list.removeIf(p -> p.id.equals(passId));
        }
        //Update pass list
        passes.removeIf(pass -> pass.id.equals(passId));
    }

    public void notifyEntrance(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b != null && p != null){
            peopleCurrentlyIn.get(b).add(p);
        }
    }

    public void notifyExit(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b != null && p != null){
            peopleCurrentlyIn.get(b).remove(p);
        }
    }

    public Pass[] getUsersIn(String buildingId) {
        Building b = getBuilding(buildingId);
        if(b == null) return new Pass[0];
        return (Pass[])peopleCurrentlyIn.get(b).toArray();
    }

    public boolean isAllowed(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b == null || p == null) return false;
        return permissions.get(passId).contains(b);
    }

    private Building getBuilding(String id){
        Optional<Building> ob = buildings.stream().filter(building -> building.id.equals(id)).findFirst();
        return ob.orElse(null);
    }

    private Pass getPass(String id){
        Optional<Pass> op = passes.stream().filter(pass -> pass.id.equals(id)).findFirst();
        return op.orElse(null);
    }
}
