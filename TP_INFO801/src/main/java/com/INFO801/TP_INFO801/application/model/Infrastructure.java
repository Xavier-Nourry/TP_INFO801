package com.INFO801.TP_INFO801.application.model;

import com.INFO801.TP_INFO801.access_system.remoteConnections;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.util.ArrayList;

public class Infrastructure {
    public ArrayList<Building> buildings;
    private final String infrastructureID;

    public Infrastructure(){
        this.buildings = new ArrayList<>();
        this.infrastructureID = "Infrastructure";
        try {
            generateBuildings();
        } catch (InterruptedException e) {
            System.out.println(infrastructureID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }

    private void generateBuildings() throws InterruptedException {
        // Connexion à l'espace de tuple
        RemoteSpace ts = remoteConnections.remoteSpaceConnexion(infrastructureID);
        assert ts != null;

        //On récupère tous les buildings
        Object[] newBuilding = ts.getp(
                new ActualField(com.INFO801.TP_INFO801.access_system.Building.BUILDING_CREATION),
                new FormalField(String.class),
                new FormalField(Integer.class)
        );
        String buildingId;
        Integer nbDoors;
        while (newBuilding != null){
            buildingId = (String) newBuilding[1];
            nbDoors = (Integer) newBuilding[2];
            buildings.add(new Building(buildingId, nbDoors));

            newBuilding = ts.getp(
                    new ActualField(com.INFO801.TP_INFO801.access_system.Building.BUILDING_CREATION),
                    new FormalField(String.class),
                    new FormalField(Integer.class)
            );
        }
    }

    public void runAll(){
        for (Building building : buildings)
            building.runAll();
    }
}