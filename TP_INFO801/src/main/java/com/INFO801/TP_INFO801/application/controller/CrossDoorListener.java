package com.INFO801.TP_INFO801.application.controller;

import com.INFO801.TP_INFO801.application.model.Door;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrossDoorListener implements ActionListener {
    private Door door;

    public CrossDoorListener(Door door) {
        this.door = door;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.door.crossing();
        System.out.println("Passe la porte : "+ this.door.id);
    }
}
