package com.INFO801.TP_INFO801.access_system;

public class InternalSwipeCardReader extends SwipeCardReader {
    public InternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "Internal");
        direction = CrossingManager.OUT_DIRECTION;
    }

    @Override
    public void startLightThreads() {
        // On lance le processus de voyant vert
        Light greenLight = new Light(readerID, Light.GREEN);
        new Thread(greenLight).start();

        // On garde l'id du voyant en attribut
        greenLightID = greenLight.getLightID();
    }
}
