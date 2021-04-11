package com.INFO801.TP_INFO801.database_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PassServer extends Remote {
    void triggerAlarm(String buildingId) throws RemoteException;
    void enter(String buildingId, String passId);
    void exit(String buildingId, String passId);
    void createPass(String id, String firstName, String lastName, String[] authorizedBuildings);
    void deletePass(String passId);
    Pass[] getUsersIn(String buildingId);
    boolean canEnter(String buildingId, String passId);
}
