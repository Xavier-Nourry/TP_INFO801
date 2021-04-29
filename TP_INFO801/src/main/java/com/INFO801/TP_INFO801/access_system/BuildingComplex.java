package com.INFO801.TP_INFO801.access_system;

import com.INFO801.TP_INFO801.database_server.PassServer;

import java.rmi.RemoteException;

public class BuildingComplex {
    public static void main(String[] args){
        com.INFO801.TP_INFO801.database_server.Building[] dbBuildings;
        String buildingComplexID = "BuildingComplex";
        try {
            // On récupère les buildings sur le server
            PassServer dbManager = remoteConnections.remoteDatabaseConnection(buildingComplexID);
            assert dbManager != null;
            dbBuildings = dbManager.getBuildings();

        } catch (RemoteException e) {
            System.out.println(buildingComplexID +": error while communicating with the db");
            e.printStackTrace();
            return;
        }

        // On crée les building du server dans le système d'accès:
        Building accessSystemBuilding;
        for (com.INFO801.TP_INFO801.database_server.Building dbBuilding : dbBuildings) {
            accessSystemBuilding = new Building(dbBuilding);
            new Thread(accessSystemBuilding).start();
        }
    }
}
