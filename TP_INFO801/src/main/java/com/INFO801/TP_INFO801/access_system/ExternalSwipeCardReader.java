package com.INFO801.TP_INFO801.access_system;

public class ExternalSwipeCardReader extends SwipeCardReader {
    public String redLightName;

    public ExternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "External");
        direction = CrossingManager.IN_DIRECTION;
    }

    @Override
    public void startLights() {
        Light gLight = new Light(readerName, Light.GREEN);
        greenLightName = gLight.lightName;
        Thread greenLight = new Thread(gLight);
        greenLight.start();
        Light rLight = new Light(readerName, Light.RED);
        redLightName = rLight.lightName;
        Thread redLight = new Thread(rLight);
        redLight.start();
    }

    public String getRedLightID() {
        return redLightName;
    }
}
