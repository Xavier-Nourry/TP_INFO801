package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.controller.StartFireListener;
import com.INFO801.TP_INFO801.application.model.Building;
import com.INFO801.TP_INFO801.application.model.Door;

import javax.swing.*;
import java.awt.*;

public class BuildingContainer extends JPanel implements UIComponent{
    private Building building;
    private JButton fireButton;

    public BuildingContainer(Building buidling){
        this.building = buidling;
        this.setLayout(new GridLayout(3, 1));
        fillUIFromModel();
    }

    @Override
    public void fillUIFromModel() {
        this.add(new JLabel(this.building.id));
        JPanel firePanel = new JPanel(new FlowLayout(0));
        this.fireButton = new JButton(Constants.START_FIRE);
        this.fireButton.addActionListener(new StartFireListener(this.building, this.fireButton));
        firePanel.add(this.fireButton);
        this.add(firePanel);
        JPanel doorsPanel = new JPanel(new GridLayout(this.building.doors.length, 1));
        for(Door door : this.building.doors){
            doorsPanel.add(new DoorContainer(door));
        }
        this.add(doorsPanel);
        // TODO : gérer les alarmes
    }
}
