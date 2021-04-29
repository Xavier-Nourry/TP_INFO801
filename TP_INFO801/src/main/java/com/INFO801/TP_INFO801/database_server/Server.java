package com.INFO801.TP_INFO801.database_server;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Date;

import static java.util.stream.Collectors.toList;

public class Server implements PassServer {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1099;

    private final PassPermissionManager manager;

    private Server(){
        manager = new PassPermissionManager();
    }

    private void setup(){
        manager.createBuilding("LABOS", 2);
        manager.createBuilding("LAUZIERES", 2);
        manager.createPass("1","Jean","Valjean",new String[]{"LAUZIERES"});
        manager.createPass("2","Julien","Sorel",new String[]{"LABOS","LAUZIERES"});
        manager.notifyEntrance("LABOS","1");
        manager.triggerAlarm("LABOS");
    }

    public static void main(String[] args){
        try{
            Server serv = new Server();
            serv.setup();
            PassServer stub = (PassServer) UnicastRemoteObject.exportObject(serv, 0);
            Registry reg = LocateRegistry.createRegistry(PORT);
            reg.bind("PassServer", stub);
            System.out.println("Serveur démarré avec succès");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void triggerAlarm(String buildingId) throws RemoteException {
        System.out.println("[RPC] Alarm on "+buildingId);
        manager.triggerAlarm(buildingId);
    }

    @Override
    public void shutOffAlarm(String buildingId) throws RemoteException {
        System.out.println("[RPC] Alarm off "+buildingId);
        manager.shutOffAlarm(buildingId);
    }

    @Override
    public void enter(String buildingId, String passId) {
        System.out.println("[RPC] "+passId+" enters "+buildingId);
        manager.notifyEntrance(buildingId, passId);
    }

    @Override
    public void exit(String buildingId, String passId) {
        System.out.println("[RPC] "+passId+" leaves "+buildingId);
        manager.notifyExit(buildingId, passId);
    }

    @Override
    public void createPass(String passId, String firstName, String lastName, String[] authorizedBuildings) {
        System.out.println("[RPC] new pass :"+passId +" "+firstName+" "+lastName+" "+Arrays.deepToString(authorizedBuildings));
        manager.createPass(passId, firstName, lastName, authorizedBuildings);
    }

    @Override
    public void deletePass(String passId) {
        System.out.println("[RPC] delete pass : "+passId);
        manager.deletePass(passId);
    }

    @Override
    public Pass[] getUsersIn(String buildingId) {
        System.out.println("[RPC] get users in : "+buildingId);
        return manager.getUsersIn(buildingId);
    }

    @Override
    public Building[] getBuildings() throws RemoteException {
        System.out.println("[RPC] get buildings");
        Building[] buildings = new Building[manager.buildings.size()];
        manager.buildings.toArray(buildings);
        return buildings;
    }

    @Override
    public Pass[] getPasses() throws RemoteException {
        System.out.println("[RPC] get passes");
        Pass[] passes = new Pass[manager.passes.size()];
        manager.passes.toArray(passes);
        return passes;
    }

    @Override
    public boolean canEnter(String buildingId, String passId) {
        System.out.println("[RPC] can enter : "+passId+" "+buildingId);
        return manager.isAllowed(buildingId, passId);
    }

    @Override
    public LogEntry[] getLogs(){
        System.out.println("[RPC] get logs");
        return manager.getLogs();
    }

    @Override
    public LogEntry[] getLogsAfter(Date begin){
        return Arrays.stream(manager.getLogs())
                .filter(l -> l.d.getTime() >= begin.getTime())
                .toArray(LogEntry[]::new);
    }
}
