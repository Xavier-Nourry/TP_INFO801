package com.INFO801.TP_INFO801.control_room.model;

import com.INFO801.TP_INFO801.database_server.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PassManagerClient {
    private PassServer server;
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public PassManagerClient(){
        try{
            Registry reg = LocateRegistry.getRegistry(Server.HOST,Server.PORT);
            server = (PassServer) reg.lookup("PassServer");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pass[] getPasses(){
        try {
            return server.getPasses();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Building[] getBuildings() {
        try {
            return server.getBuildings();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pass[] getUsersIn(Building building) {
        try {
            return server.getUsersIn(building.id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createPass(String id, String firstName, String lastName, String[] authorizedBuildings){
        try {
            server.createPass(id,firstName,lastName,authorizedBuildings);
            changes.firePropertyChange("passes", null, server.getPasses());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void deletePass(Pass p) {
        try {
            server.deletePass(p.id);
            changes.firePropertyChange("passes", null, server.getPasses());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    public List<String> getLogsAsString() {
        try {
            return Arrays.stream(server.getLogs()).map(LogEntry::toString).collect(Collectors.toList());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
