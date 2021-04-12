package com.INFO801.TP_INFO801.control_room.model;

import com.INFO801.TP_INFO801.database_server.Pass;
import com.INFO801.TP_INFO801.database_server.PassServer;
import com.INFO801.TP_INFO801.database_server.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class DataClient {
    private PassServer server;

    public DataClient(){
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
}
