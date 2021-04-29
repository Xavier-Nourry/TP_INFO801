package com.INFO801.TP_INFO801.control_room.controller;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.control_room.view.PassInfo;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePassListener implements ActionListener {
    private final PassManagerClient client;
    private final JList<PassInfo> list;

    public DeletePassListener(PassManagerClient client, JList<PassInfo> list) {
        this.client = client;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Pass p = list.getSelectedValue().getData();
        client.deletePass(p);
    }
}
