package com.INFO801.TP_INFO801.application.controller;

import com.INFO801.TP_INFO801.application.view.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFireListener implements ActionListener {
    // TODO : à décommenter
    //private final Building building;
    private final JButton fire;

    public StartFireListener(JButton fire) { // TODO : ajouter building aux paramètres
        // TODO : à décommenter
        //this.building = building;
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
