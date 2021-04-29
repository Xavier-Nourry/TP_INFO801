package com.INFO801.TP_INFO801.control_room.model;

import com.INFO801.TP_INFO801.database_server.PassServer;
import com.INFO801.TP_INFO801.database_server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public TestClient(){
        Registry reg = null;
        try {
            reg = LocateRegistry.getRegistry(Server.HOST,Server.PORT);
            PassServer server = (PassServer) reg.lookup("PassServer");
            server.enter("LAUZIERES","2");
            server.triggerAlarm("LAUZIERES");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new TestClient();
    }
}
