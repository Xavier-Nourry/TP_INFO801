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
    private final String managerID;
    private final String buildingID;
    private final RemoteSpace ts;
    private PassServer dbManager;

    public AuthorizationManager(String buildingName) {
        this.managerID = buildingName + " - authorizationManager";
        this.buildingID = buildingName;
        try {
            // Connexion à la base de données
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", Server.PORT);
            dbManager = (PassServer) reg.lookup("PassServer");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(managerID + " : error while communicating with the db");
            e.printStackTrace();
        }

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(buildingID);
    }

    @Override
    public void run() {
        monitor();
    }

    private void monitor() {
        try {
            monitorAuthorizations();
        } catch (InterruptedException e) {
            System.out.println(buildingID + " : error while communicating with the tuple space");
            e.printStackTrace();
            return;
        } catch (RemoteException remoteException) {
            System.out.println(buildingID + " : error while communicating with the dataBase");
            remoteException.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    private void monitorAuthorizations() throws InterruptedException, RemoteException {
        Object[] request = ts.get(
                new ActualField(buildingID),
                new FormalField(String.class),
                new ActualField(CrossingManager.CROSSING_REQUEST),
                new FormalField(String.class),
                new FormalField(String.class)
        );
        String doorName = (String) request[1];
        String direction = (String) request[3];
        String swipeCardId = (String) request[4];

        boolean authorization;
        if (direction.equals(CrossingManager.OUT_DIRECTION))
            authorization = true;
        else
            authorization = dbManager.canEnter(buildingID, swipeCardId);

        if (authorization)
            ts.put(doorName, AUTHORIZED_CROSSER, direction, swipeCardId);

        ts.put(doorName, OPENING_AUTHORIZATION, direction, authorization, swipeCardId);
    }
}
