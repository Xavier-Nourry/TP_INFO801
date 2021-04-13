package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class LockTimer implements Runnable {
    public static final Object ACTIVATION = "ActiveLockTimer";
    private final String doorName;
    private final String timerName;

    public LockTimer(String doorName) {
        this.doorName = doorName;
        this.timerName = doorName + " - LockTimer";
    }

    @Override
    public void run() {
        RemoteSpace ts = TupleSpace.remoteSpaceConnexion(timerName);
        assert ts != null;

        try {
            ts.put(timerName, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.monitorTimer(ts);
    }

    private void monitorTimer(RemoteSpace ts) {
        try {
            Object[] activation = ts.get(new ActualField(doorName), new ActualField(ACTIVATION), new FormalField(String.class));
            new Thread(new LockTimer(doorName)).start();
            Object[] counter = ts.get(new ActualField(timerName), new FormalField(int.class));
            ts.put(timerName, (int)counter[1]+1);
            Thread.sleep(30000);
            Object[] AuthorizedCrosser = ts.getp(new ActualField(doorName), new ActualField(AuthorizationManager.AUTHORIZED_CROSSER), new FormalField(String.class), new ActualField(activation[2]));

            if (AuthorizedCrosser != null){
                counter = ts.get(new ActualField(timerName), new FormalField(int.class));
                int updatedCounter = (int)counter[1] - 1;
                ts.put(timerName, updatedCounter);
                if (updatedCounter > 0){
                    ts.put(doorName, Door.LOCKING, true);
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
