package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.controller.CrossDoorListener;
import com.INFO801.TP_INFO801.application.model.Door;
import com.INFO801.TP_INFO801.application.model.LightIndicator;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class DoorContainer extends JPanel implements UIComponent, Observer {
    private final Door door;
    private JLabel id;
    private JLabel state;
    private EntryLightsContainer entryLightsContainer;
    private ExitLightsContainer exitLightsContainer;
    private PassMenuContainer passMenuContainer;
    private JButton crossDoor;

    public DoorContainer(Door door){
        this.door = door;
        this.door.addObserver(this);
        this.setLayout(new GridLayout(1, 6));
        fillUIFromModel();
    }

    public void fillUIFromModel() {
        this.id = new JLabel(this.door.id);

        this.state = new JLabel(Constants.CLOSED);
        this.state.setForeground(Constants.CLOSED_COLOR);

        this.entryLightsContainer = new EntryLightsContainer(this.door.ogli, this.door.orli);
        this.exitLightsContainer = new ExitLightsContainer(this.door.igli, this.door.irli);

        this.passMenuContainer = new PassMenuContainer(this.door);

        this.crossDoor = new JButton(Constants.CROSS_DOOR);
        // TODO : supprimer second paramètre de CrossDoorListener
        this.crossDoor.addActionListener(new CrossDoorListener(this.door, this.crossDoor));

        this.add(this.id);
        this.add(this.state);
        this.add(this.entryLightsContainer);
        this.add(this.exitLightsContainer);
        this.add(this.passMenuContainer);
        this.add(this.crossDoor);
    }


    @Override
    public void update(Observable o, Object arg) {
        manageState();
        notAllowedCrossingDetection();
    }

    private void notAllowedCrossingDetection() {
        if(this.door.notAllowedCross){
            JOptionPane.showMessageDialog(getParent(), Constants.NOT_ALLOWED_CROSSING + this.door.id);
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
