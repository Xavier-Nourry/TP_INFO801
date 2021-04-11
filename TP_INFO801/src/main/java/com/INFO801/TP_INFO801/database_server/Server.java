package com.INFO801.TP_INFO801.database_server;


import com.INFO801.TP_INFO801.database_server.PassServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements PassServer {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1099;

    private PassPermissionManager manager;

    private Server(){
        manager = new PassPermissionManager();
    }

    private void setup(){
        manager.createBuilding("LABOS");
        manager.createBuilding("LAUZIERES");
        manager.createPass("1","Jean","Valjean",new String[]{"LAUZIERES"});
        manager.createPass("2","Julien","Sorel",new String[]{"LABOS","LAUZIERES"});
    }

    public static void main(String[] args){
        try{
            Server serv = new Server();
            serv.setup();
            PassServer stub = (PassServer) UnicastRemoteObject.exportObject(serv, 0);
            Registry reg = LocateRegistry.createRegistry(PORT);
            reg.bind("PassServer", stub);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void triggerAlarm(String buildingId) throws RemoteException {

    }

    @Override
    public void enter(String buildingId, String passId) {
        manager.notifyEntrance(buildingId, passId);
    }

    @Override
    public void exit(String buildingId, String passId) {
        manager.notifyExit(buildingId, passId);
    }

    @Override
    public void createPass(String passId, String firstName, String lastName, String[] authorizedBuildings) {
        manager.createPass(passId, firstName, lastName, authorizedBuildings);
    }

    @Override
    public void deletePass(String passId) {
        manager.deletePass(passId);
    }

    @Override
    public Pass[] getUsersIn(String buildingId) {
        return manager.getUsersIn(buildingId);
    }

    @Override
    public boolean canEnter(String buildingId, String passId) {
        return manager.isAllowed(buildingId, passId);
    }
}
