package com.INFO801.TP_INFO801.access_system;

import com.INFO801.TP_INFO801.database_server.PassServer;
import com.INFO801.TP_INFO801.database_server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BuildingComplex {
    public static void main(String[] args){
        com.INFO801.TP_INFO801.database_server.Building[] dbBuildings;
        try {
            // On récupère les buildings sur le server
            Registry reg = LocateRegistry.getRegistry(Server.HOST, Server.PORT);
            PassServer dbManager = (PassServer) reg.lookup("PassServer");
            dbBuildings = dbManager.getBuildings();

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Building complex : error while communicating with the db");
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
