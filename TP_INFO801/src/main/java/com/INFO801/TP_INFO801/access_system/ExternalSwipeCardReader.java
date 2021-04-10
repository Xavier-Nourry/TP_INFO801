package com.INFO801.TP_INFO801.access_system;

public class ExternalSwipeCardReader extends SwipeCardReader {
    public ExternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "External");
        direction = IN_DIRECTION;
    }

    @Override
    public void startLights() {
        Thread greenLight = new Thread(new Light(readerName, Light.GREEN));
        greenLight.start();
        Thread redLight = new Thread(new Light(readerName, Light.RED));
        redLight.start();
    }
}
