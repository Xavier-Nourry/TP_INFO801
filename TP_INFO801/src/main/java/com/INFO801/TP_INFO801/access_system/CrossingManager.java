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
    private final String doorID;
    private final String managerName;
    private PassServer dbManager;
    private final String buildingID;

    public CrossingManager(String buildingID, String doorID) {
        this.buildingID = buildingID;
        this.doorID = doorID;
        this.managerName = doorID + "- CrossingManager";
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
        Thread laserSensor = new Thread(new LaserSensor(doorID));
        laserSensor.start();
        ExternalSwipeCardReader extSCR = new ExternalSwipeCardReader(buildingID, doorID);
        Thread externalSwipeCardReader = new Thread(extSCR);
        externalSwipeCardReader.start();
        InternalSwipeCardReader inSCR = new InternalSwipeCardReader(buildingID, doorID);
        Thread internalSwipeCardReader = new Thread(inSCR);
        internalSwipeCardReader.start();
        Thread lockManager = new Thread(new LockManager(doorID, extSCR.redLightName, extSCR.greenLightName, inSCR.greenLightName));
        lockManager.start();

        Thread lockTimer= new Thread(new LockTimer(doorID));
        lockTimer.start();

        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(managerName);
        assert ts != null;

        while (true){
            monitorCrossing(ts);
        }
    }

    private void monitorCrossing(RemoteSpace ts) {
        try {
            ts.get(new ActualField(doorID), new ActualField(CROSSING));
            Object[] crosser = ts.getp(new ActualField(doorID), new ActualField(AuthorizationManager.AUTHORIZED_CROSSER),
                    new FormalField(String.class), new FormalField(String.class));
            ts.put(doorID, Door.LOCKING, true);
            if (crosser != null){
                if (crosser[2].equals(SwipeCardReader.IN_DIRECTION)){
                    dbManager.enter(buildingID, (String) crosser[3]);
                }else{
                    dbManager.exit(buildingID, (String) crosser[3]);
                }
            }else{
                ts.put(buildingID, UNAUTHORIZED_CROSSING);
            }
        } catch (InterruptedException | RemoteException e) {
            System.out.println(doorID + " : error while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
