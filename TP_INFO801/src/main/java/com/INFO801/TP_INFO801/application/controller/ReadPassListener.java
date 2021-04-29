package com.INFO801.TP_INFO801.application.controller;

import com.INFO801.TP_INFO801.application.model.Door;
import com.INFO801.TP_INFO801.application.view.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadPassListener implements ActionListener {
    private Door door;
    private JTextField passID;
    private JComboBox badgerChoice;

    public ReadPassListener(Door door, JTextField passID, JComboBox<String> badgerChoice) {
        this.door = door;
        this.passID = passID;
        this.badgerChoice = badgerChoice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = this.passID.getText();
        String choice = String.valueOf(this.badgerChoice.getSelectedItem());
        System.out.println(choice);
        if (choice.equals(Constants.ENTRY)) {
            this.door.badgingFromOutside(id);
            System.out.println("Badgeage extérieur : " + this.door.id);
        } else if(choice.equals(Constants.EXIT)){
            this.door.badgingFromInside(id);
            System.out.println("Badgeage intérieur : " + this.door.id);
        }
    }
}