package com.INFO801.TP_INFO801.control_room.model;

import com.INFO801.TP_INFO801.database_server.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PassManagerClient {
    private Date lastCheckTime;
    private PassServer server;
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public PassManagerClient() throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(Server.HOST,Server.PORT);
        server = (PassServer) reg.lookup("PassServer");
        lastCheckTime = new Date(System.currentTimeMillis());

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    checkUpdates();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void checkUpdates() throws RemoteException {
        LogEntry[] newLogs = server.getLogsAfter(lastCheckTime);
        lastCheckTime.setTime(System.currentTimeMillis());
        String[] typesToUpdate = Arrays.stream(newLogs).map(LogEntry::getType).distinct().toArray(String[]::new);

        if(typesToUpdate.length>0){
            changes.firePropertyChange("logs", null, getLogsAsString());
        }

        for(String type : typesToUpdate){
            switch (type) {
                case "ENTER":
                case "EXIT":
                    changes.firePropertyChange("users", null, null);
                    break;
                case "FIRE_ON":
                case "FIRE_OFF":
                    changes.firePropertyChange("buildings", null, server.getBuildings());
                    break;
                default:
                    break;
            }
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

    public String[] getLogsAsString() {
        try {
            LogEntry[] entries = server.getLogs();
            String[] res = new String[entries.length];
            return Arrays
                    .stream(entries)
                    .sorted(
                            (LogEntry e, LogEntry e2)->((int)(e2.getDate().getTime()-e.getDate().getTime()))
                    )
                    .map(LogEntry::toString)
                    .collect(Collectors.toList())
                    .toArray(res);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
