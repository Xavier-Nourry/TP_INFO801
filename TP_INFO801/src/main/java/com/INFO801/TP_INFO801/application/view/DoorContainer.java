package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.controller.CrossDoorListener;
import com.INFO801.TP_INFO801.application.model.Door;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DoorContainer extends JPanel implements UIComponent, Observer {
    private final Door door;
    private JLabel state;

    public DoorContainer(Door door){
        this.door = door;
        this.door.addObserver(this);
        this.setLayout(new GridLayout(1, 6));
        fillUIFromModel();
    }

    public void fillUIFromModel() {
        JLabel id = new JLabel(this.door.id);

        this.state = new JLabel(Constants.CLOSED);
        this.state.setForeground(Constants.CLOSED_COLOR);

        EntryLightsContainer entryLightsContainer = new EntryLightsContainer(this.door.ogli, this.door.orli);
        ExitLightsContainer exitLightsContainer = new ExitLightsContainer(this.door.igli, this.door.irli);

        PassMenuContainer passMenuContainer = new PassMenuContainer(this.door);

        JButton crossDoor = new JButton(Constants.CROSS_DOOR);
        crossDoor.addActionListener(new CrossDoorListener(this.door));

        this.add(id);
        this.add(this.state);
        this.add(entryLightsContainer);
        this.add(exitLightsContainer);
        this.add(passMenuContainer);
        this.add(crossDoor);
    }


    @Override
    public void update(Observable o, Object arg) {
        manageState();
        notAllowedCrossingDetection();
    }

    private void notAllowedCrossingDetection() {
        if(this.door.notAllowedCross){
            JOptionPane.showMessageDialog(getParent(), Constants.NOT_ALLOWED_CROSSING + this.door.id);
            this.door.notAllowedCross = false;
        }
    }

    private void manageState(){
        if(this.door.open){
            this.state.setText(Constants.OPEN);
            this.state.setForeground(Constants.OPEN_COLOR);
        }else{
            this.state.setText(Constants.CLOSED);
            this.state.setForeground(Constants.CLOSED_COLOR);
        }
    }
}
