package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.Door;
import com.INFO801.TP_INFO801.application.model.LightIndicator;

import javax.swing.*;
import java.awt.*;

public class DoorContainer extends JPanel implements UIComponent{
    private final Door door;

    public DoorContainer(Door door){
        this.door = door;
        this.setLayout(new GridLayout(1, 5));
        fillUIFromModel();
    }

    public void fillUIFromModel() {
        JLabel doorID = new JLabel(this.door.id);
        JLabel doorState = new JLabel(String.valueOf((this.door.open)));
        doorState.setForeground(Color.RED);
        //PassMenu passMenu = new PassMenu(door);
        this.add(doorID);
        this.add(doorState);
        /*this.add(entryLights);
        this.add(exitLights);*/
        //this.add(passMenu);
    }
}
