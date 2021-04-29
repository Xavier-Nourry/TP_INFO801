package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockManager implements Runnable {
    private final String doorID;
    private final String buildingID;
    private final String managerName;
    private String redLightName;
    private String greenExternalLightName;
    private String greenInternalLightName;

    public LockManager(String buildingID, String doorID) {
        this.doorID = doorID;
        this.buildingID = buildingID;
        this.managerName = doorID + " - LockManager";
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(managerName);
        assert ts != null;

        ExternalSwipeCardReader extSCR = new ExternalSwipeCardReader(buildingID, doorID);
        Thread externalSwipeCardReader = new Thread(extSCR);
        externalSwipeCardReader.start();
        InternalSwipeCardReader inSCR = new InternalSwipeCardReader(buildingID, doorID);
        Thread internalSwipeCardReader = new Thread(inSCR);
        internalSwipeCardReader.start();

        redLightName = extSCR.redLightName;
        greenExternalLightName = extSCR.greenLightName;
        greenInternalLightName = inSCR.greenLightName;

        Thread lockTimer= new Thread(new LockTimer(doorID));
        lockTimer.start();

        this.monitorLocking(ts);
    }

    private void monitorLocking(RemoteSpace ts) {
        try {
            Object[] authorisation = ts.get(new ActualField(doorID), new ActualField(AuthorizationManager.OPENING_AUTHORIZATION),
                    new FormalField(String.class), new FormalField(boolean.class),  new FormalField(String.class)
            );
            if ((boolean) authorisation[3]){
                String lightName = (authorisation[2].equals(CrossingManager.IN_DIRECTION))?greenExternalLightName:greenInternalLightName;
                ts.put(lightName, Light.LIGHTING, true);
                ts.put(doorID, Door.LOCKING, false);
                ts.put(doorID, LockTimer.ACTIVATION, authorisation[4]);
                Thread.sleep(5000);
                ts.put(lightName, Light.LIGHTING, false);
            }else{
                ts.put(redLightName, Light.LIGHTING, true);
                Thread.sleep(10000);
                ts.put(redLightName, Light.LIGHTING, false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
