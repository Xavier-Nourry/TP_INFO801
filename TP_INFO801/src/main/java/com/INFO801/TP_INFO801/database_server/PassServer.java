package com.INFO801.TP_INFO801.database_server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface PassServer extends Remote {
    void triggerAlarm(String buildingId) throws RemoteException;
    void shutOffAlarm(String buildingId) throws RemoteException;
    void enter(String buildingId, String passId) throws RemoteException;
    void exit(String buildingId, String passId) throws RemoteException;
    void createPass(String id, String firstName, String lastName, String[] authorizedBuildings) throws RemoteException;
    void deletePass(String passId) throws RemoteException;
    Pass[] getUsersIn(String buildingId) throws RemoteException;
    Building[] getBuildings() throws RemoteException;
    Pass[] getPasses() throws RemoteException;
    boolean canEnter(String buildingId, String passId) throws RemoteException;
    LogEntry[] getLogs();

    LogEntry[] getLogsAfter(Date begin);
}
