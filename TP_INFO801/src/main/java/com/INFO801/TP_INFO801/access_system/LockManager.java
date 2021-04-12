package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockManager implements Runnable {
    private final String doorName;
    private final String managerName;
    private final String redLightName;
    private final String greenExternalLightName;
    private final String greenInternalLightName;

    public LockManager(String doorName, String redLightName, String greenExternalLightName, String greenInternalLightName) {
        this.doorName = doorName;
        this.managerName = doorName + " - LockManager";
        this.redLightName = redLightName;
        this.greenExternalLightName = greenExternalLightName;
        this.greenInternalLightName = greenInternalLightName;
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(managerName);
        assert ts != null;

        this.monitorLocking(ts);
    }

    private void monitorLocking(RemoteSpace ts) {
        try {
            Object[] authorisation = ts.get(new ActualField(doorName), new ActualField(AuthorizationManager.OPENING_AUTHORIZATION),
                    new FormalField(String.class), new FormalField(boolean.class),  new FormalField(String.class)
            );
            if ((boolean) authorisation[3]){
                String lightName = (authorisation[2].equals(SwipeCardReader.IN_DIRECTION))?greenExternalLightName:greenInternalLightName;
                ts.put(lightName, Light.LIGHTING, true);
                ts.put(doorName, Door.LOCKING, false);
                ts.put(doorName, LockTimer.ACTIVATION, authorisation[4]);
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
