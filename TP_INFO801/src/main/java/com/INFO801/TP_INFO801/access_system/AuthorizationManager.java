package com.INFO801.TP_INFO801.access_system;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

public class AuthorizationManager implements Runnable {
    private static final String OPENING_AUTHORIZATION = "OpeningAuthorization";
    private String managerName;
    private String buildingName;

    public AuthorizationManager(String buildingName) {
        this.managerName = buildingName + " - authorizationManager";
        this.buildingName = buildingName;
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
                    new ActualField(Door.CROSSING_REQUEST),
                    new FormalField(String.class),  new FormalField(int.class)
            );
            String doorName = (String) request[1];
            String direction = (String) request[3];
            int swipeCardId = (int) request[4];
            boolean authorization;
            if (direction.equals(SwipeCardReader.OUT_DIRECTION))
                authorization = true;
            else
                authorization = false; //TODO: ajouter la gestion selon la bdd

            ts.put(doorName, OPENING_AUTHORIZATION, authorization);

        } catch (InterruptedException e) {
            System.out.println(managerName + " : erreur while communicating with the tuple space");
            e.printStackTrace();
        }
    }
}
