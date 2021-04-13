package com.INFO801.TP_INFO801.access_system;

import com.INFO801.TP_INFO801.database_server.PassServer;
import com.INFO801.TP_INFO801.database_server.Server;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuthorizationManager implements Runnable {
    public static final String OPENING_AUTHORIZATION = "OpeningAuthorization";
    public static final String AUTHORIZED_CROSSER = "AuthorizedCrosser";
    private final String managerName;
    private final String buildingName;
    private PassServer dbManager;

    public AuthorizationManager(String buildingName) {
        this.managerName = buildingName + " - authorizationManager";
        this.buildingName = buildingName;
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", Server.PORT);
            dbManager = (PassServer) reg.lookup("PassServer");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(managerName + " : error while communicating with the db");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(managerName);
        assert ts != null;

        while (true){
            monitorAuthorizations(ts);
        }
    }

    private void monitorAuthorizations(RemoteSpace ts) {
        try {
            Object[] request = ts.get(
                    new ActualField(buildingName), new FormalField(String.class),
                    new ActualField(CrossingManager.CROSSING_REQUEST),
                    new FormalField(String.class),  new FormalField(String.class)
            );
            String doorName = (String) request[1];
            String direction = (String) request[3];
            String swipeCardId = (String) request[4];
            boolean authorization;
            if (direction.equals(SwipeCardReader.OUT_DIRECTION)){
                authorization = true;
            }
            else{
                authorization = dbManager.canEnter(buildingName, swipeCardId);
            }
            if (authorization)
                ts.put(doorName, AUTHORIZED_CROSSER, direction, swipeCardId);
            ts.put(doorName, OPENING_AUTHORIZATION, direction, authorization, swipeCardId);

        } catch (InterruptedException | RemoteException e) {
            System.out.println(managerName + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
