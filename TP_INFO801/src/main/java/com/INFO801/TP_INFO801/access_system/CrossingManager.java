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
    private static final String UNAUTHORIZED_CROSSING = "UnauthorizedCrossing";
    private final String doorName;
    private final String managerName;
    private PassServer dbManager;
    private final String buildingName;

    public CrossingManager(String buildingName, String doorName) {
        this.buildingName = buildingName;
        this.doorName = doorName;
        this.managerName = doorName + "- CrossingManager";
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
            monitorCrossing(ts);
        }
    }

    private void monitorCrossing(RemoteSpace ts) {
        try {
            ts.get(new ActualField(doorName), new ActualField(Door.CROSSING));
            Object[] crosser = ts.getp(new ActualField(doorName), new ActualField(AuthorizationManager.AUTHORIZED_CROSSER),
                    new FormalField(String.class), new FormalField(String.class));
            ts.put(doorName, Door.LOCKING, true);
            if (crosser != null){
                if (crosser[2].equals(SwipeCardReader.IN_DIRECTION)){
                    dbManager.enter(buildingName, (String) crosser[3]);
                }else{
                    dbManager.exit(buildingName, (String) crosser[3]);
                }
            }else{
                ts.put(buildingName, UNAUTHORIZED_CROSSING);
            }
        } catch (InterruptedException | RemoteException e) {
            System.out.println(doorName + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
