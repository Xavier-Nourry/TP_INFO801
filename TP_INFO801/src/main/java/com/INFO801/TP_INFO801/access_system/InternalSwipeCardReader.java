package com.INFO801.TP_INFO801.access_system;

public class InternalSwipeCardReader extends SwipeCardReader {
    public InternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "Internal");
        direction = OUT_DIRECTION;
    }

    @Override
    public void startLights() {
        Thread greenLight = new Thread(new Light(readerName, Light.GREEN));
        greenLight.start();
    }
}
