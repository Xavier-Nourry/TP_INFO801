package com.INFO801.TP_INFO801.access_system;

public class ExternalSwipeCardReader extends SwipeCardReader {
    public String redLightID;

    public ExternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "External");
        direction = CrossingManager.IN_DIRECTION;
    }

    @Override
    public void startLightThreads() {
        // On lance le processus de voyant vert
        Light greenLight = new Light(readerID, Light.GREEN);
        new Thread(greenLight).start();

        // On lance le processus de voyant rouge
        Light redLight = new Light(readerID, Light.RED);
        new Thread(redLight).start();

        // On garde les id des voyants en attribut
        greenLightID = greenLight.getLightID();
        redLightID = redLight.getLightID();
    }

    public String getRedLightID() {
        return redLightID;
    }
}
