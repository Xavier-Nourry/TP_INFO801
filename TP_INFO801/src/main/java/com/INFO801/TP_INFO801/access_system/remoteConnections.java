package com.INFO801.TP_INFO801.access_system;

import com.INFO801.TP_INFO801.database_server.PassServer;
import com.INFO801.TP_INFO801.database_server.Server;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class remoteConnections {
    public static RemoteSpace remoteSpaceConnexion(String elementName){
        try {
            RemoteSpace ts = new RemoteSpace(TupleSpace.CLIENT_URI);
            System.out.println(elementName + " connected to tuple space");
            return ts;
        } catch (IOException e) {
            System.out.println(elementName + " didn't connect to tuple space");
            e.printStackTrace();
            return null;
        }
    }

    public static PassServer remoteDatabaseConnection(String elementName){
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", Server.PORT);
            System.out.println(elementName + " connected to database");
            return (PassServer) reg.lookup("PassServer");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(elementName + " : didn't connect to database");
            e.printStackTrace();
            return null;
        }
    }
}
