package com.INFO801.TP_INFO801.access_system;

public class InternalSwipeCardReader extends SwipeCardReader {
    public InternalSwipeCardReader(String buildingName, String doorName) {
        super(buildingName, doorName, "Internal");
        direction = OUT_DIRECTION;
    }

    @Override
    public void startLights() {
        Light gLight = new Light(readerName, Light.GREEN);
        greenLightName = gLight.lightName;
        Thread greenLight = new Thread(gLight);
        greenLight.start();
    }
}
