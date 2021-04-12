package com.INFO801.TP_INFO801.control_room.controller;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePassListener implements ActionListener {
    private final PassManagerClient client;
    private final JTextField idField;
    private final JTextField firstName;
    private final JTextField lastName;
    private final JTextArea authorizations;

    public CreatePassListener(PassManagerClient client, JTextField idField, JTextField firstName, JTextField lastName, JTextArea authorizations) {
        this.client = client;
        this.idField = idField;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorizations = authorizations;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] authorizedBuildings = authorizations.getText().split(",");
        String id = idField.getText();
        String fn = firstName.getText();
        String ln = lastName.getText();
        if(!(id.equals("") || fn.equals("") || ln.equals("") || authorizedBuildings.length==0)){
            client.createPass(id, fn, ln, authorizedBuildings);
            idField.setText("");
            firstName.setText("");
            lastName.setText("");
            authorizations.setText("");
        }
    }
}
