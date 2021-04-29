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

public class CrossingManager implements Runnable {
    public static final String CROSSING = "Crossing";
    public static final String CROSSING_REQUEST = "CrossingRequest";
    private static final String UNAUTHORIZED_CROSSING = "UnauthorizedCrossing";
    public final static String OUT_DIRECTION = "Out";
    public final static String IN_DIRECTION = "In";

    private final String buildingID;
    private final String doorID;
    private final String managerID;
    private final RemoteSpace ts;
    private PassServer dbManager;

    public CrossingManager(String buildingID, String doorID) {
        this.buildingID = buildingID;
        this.doorID = doorID;
        this.managerID = doorID + "- CrossingManager";

        try {
            // Connexion au la base de données
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", Server.PORT);
            dbManager = (PassServer) reg.lookup("PassServer");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(managerID + " : error while communicating with the db");
            e.printStackTrace();
        }

        // Connexion à l'espace de tuple
        ts = TupleSpace.remoteSpaceConnexion(managerID);
    }

    @Override
    public void run() {
        // On lance le processus de détection de passage
        new Thread(new LaserSensor(doorID)).start();

        // On lance le processus de gestion du verrouillage
        new Thread(new LockManager(buildingID, doorID)).start();

        monitor();
    }

    private void monitor() {
        try {
            monitorCrossing();
        } catch (InterruptedException e) {
            System.out.println(managerID + " : error while communicating with the tuple space");
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println(managerID + " : error while communicating with the database");
            e.printStackTrace();
        }
        monitor(); // S'appelle récursivement
    }

    private void monitorCrossing() throws InterruptedException, RemoteException {
        ts.get(new ActualField(doorID),
                new ActualField(CROSSING));

        Object[] crosser = ts.getp(
                new ActualField(doorID),
                new ActualField(AuthorizationManager.AUTHORIZED_CROSSER),
                new FormalField(String.class),
                new FormalField(String.class));

        if (crosser != null) { // Personne autorisée à entrer
            String direction = (String) crosser[2];
            String swipeCardId = (String) crosser[3];
            if (direction.equals(IN_DIRECTION))
                dbManager.enter(buildingID, swipeCardId);
            else
                dbManager.exit(buildingID, swipeCardId);

        } else // Personne non autorisée à entrer
            ts.put(buildingID, UNAUTHORIZED_CROSSING);

        // On regarde si quelqu'un d'autre est actuellement autorisé à passer
        Object[] crosser2 = ts.queryp(
                new ActualField(doorID),
                new ActualField(AuthorizationManager.AUTHORIZED_CROSSER),
                new FormalField(String.class),
                new FormalField(String.class));

        // Si personne d'autre n'a eu l'autorisation de passer en même temps,
        // on reverrouille la porte après le passage de la personne précédente
        if (crosser2 == null)
            ts.put(doorID, Door.LOCKING, true);
    }
}
