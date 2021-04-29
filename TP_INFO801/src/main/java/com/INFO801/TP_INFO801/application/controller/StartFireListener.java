package com.INFO801.TP_INFO801.application.controller;

import com.INFO801.TP_INFO801.application.model.Building;
import com.INFO801.TP_INFO801.application.view.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFireListener implements ActionListener {
    private Building building;
    private JButton fire;

    public StartFireListener(Building building, JButton fire) {
        this.building = building;
        this.fire = fire;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.fire.getText().equals(Constants.START_FIRE)){
            //TODO : à décommenter
            //this.building.notifyStartedFire();
            this.fire.setText(Constants.STOP_FIRE);
        }else if(this.fire.getText().equals(Constants.STOP_FIRE)){
            //TODO : à décommenter
            //this.building.notifyStoppedFire();
            this.fire.setText(Constants.START_FIRE);
        }
    }
}