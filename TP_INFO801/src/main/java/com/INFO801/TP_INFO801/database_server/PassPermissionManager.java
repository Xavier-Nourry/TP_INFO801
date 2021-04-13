package com.INFO801.TP_INFO801.database_server;

import java.util.*;

public class PassPermissionManager {
    public ArrayList<Building> buildings;
    public ArrayList<Pass> passes;
    private final HashMap<Pass, ArrayList<Building>> permissions;
    private final HashMap<Building, ArrayList<Pass>> peopleCurrentlyIn;
    private final Logger logs;

    public PassPermissionManager(){
        buildings = new ArrayList<>();
        passes = new ArrayList<>();
        permissions = new HashMap<>();
        peopleCurrentlyIn = new HashMap<>();
        logs = new Logger();
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
        permissions.put(p, authorizedBuildings);
    }

    public void deletePass(String passId) {
        //Update permissions
        {
            Pass p = getPass(passId);
            if(p!=null)permissions.remove(p);
        }
        //Update list of people in buildings
        {
            for(ArrayList<Pass> list : peopleCurrentlyIn.values()){
                list.removeIf(p -> p.id.equals(passId));
            }
        }
        //Update pass list
        {
            passes.removeIf(p -> p.id.equals(passId));
        }
    }

    public void notifyEntrance(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b != null && p != null){
            peopleCurrentlyIn.get(b).add(p);
            logs.logEntrance(b,p,new Date(System.currentTimeMillis()));
        }
    }

    public void notifyExit(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b != null && p != null){
            peopleCurrentlyIn.get(b).remove(p);
            logs.logExit(b,p,new Date(System.currentTimeMillis()));
        }
    }

    public void triggerAlarm(String buildingId) {
        Building b = getBuilding(buildingId);
        if(b!=null){
            b.isOnAlarm = true;
            logs.logFireAlarm(b, new Date(System.currentTimeMillis()));
        }
    }

    public void shutOffAlarm(String buildingId) {
        Building b = getBuilding(buildingId);
        if(b!=null){
            b.isOnAlarm = false;
            logs.logStopFireAlarm(b, new Date(System.currentTimeMillis()));
        }
    }

    public Pass[] getUsersIn(String buildingId) {
        Building b = getBuilding(buildingId);
        if(b == null) return new Pass[0];
        Pass[] passes = new Pass[peopleCurrentlyIn.get(b).size()];
        peopleCurrentlyIn.get(b).toArray(passes);
        return passes;
    }

    public boolean isAllowed(String buildingId, String passId) {
        Building b = getBuilding(buildingId);
        Pass p = getPass(passId);
        if(b == null || p == null) return false;
        return permissions.get(p).contains(b);
    }

    public LogEntry[] getLogs() {
        return logs.logs.toArray(new LogEntry[0]);
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
