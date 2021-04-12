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

        this.monitorTimer(ts);
    }

    private void monitorTimer(RemoteSpace ts) {
        try {
            Object[] activation = ts.get(new ActualField(doorName), new ActualField(ACTIVATION), new FormalField(String.class));
            new Thread(new LockTimer(doorName)).start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
