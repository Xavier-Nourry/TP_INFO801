package com.INFO801.TP_INFO801.application.controller;

import com.INFO801.TP_INFO801.application.model.Door;
import com.INFO801.TP_INFO801.application.view.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrossDoorListener implements ActionListener {
    private Door door;
    // TODO : à supprimer
    private JButton button;

    // TODO : supprimer second paramètre de CrossDoorListener
    public CrossDoorListener(Door door, JButton button) {
        this.door = door;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.door.crossing();
        // TODO : à supprimer
        JOptionPane.showMessageDialog(this.button.getParent(), Constants.NOT_ALLOWED_CROSSING + this.door.id);
        System.out.println("Passe la porte : "+ this.door.id);
    }
}
