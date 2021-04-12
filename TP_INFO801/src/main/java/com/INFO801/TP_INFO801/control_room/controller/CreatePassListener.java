package com.INFO801.TP_INFO801.control_room.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePassListener implements ActionListener {

    private JTextField firstName;
    private JTextField lastName;
    private JTextArea authorizations;

    public CreatePassListener(JTextField firstName, JTextField lastName, JTextArea authorizations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorizations = authorizations;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[Created]"+firstName.getText()+" "+lastName.getText()+" "+authorizations.getText());
    }
}
