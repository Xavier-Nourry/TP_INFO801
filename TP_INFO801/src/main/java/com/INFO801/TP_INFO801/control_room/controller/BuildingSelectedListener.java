package com.INFO801.TP_INFO801.control_room.controller;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.control_room.view.BuildingInfo;
import com.INFO801.TP_INFO801.control_room.view.PassListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BuildingSelectedListener implements ListSelectionListener {

    private final PassManagerClient model;
    private final PassListModel userListModel;
    private final JList<BuildingInfo> list;

    public BuildingSelectedListener(PassManagerClient model, PassListModel userListModel, JList<BuildingInfo> list) {
        this.model = model;
        this.userListModel = userListModel;
        this.list = list;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        userListModel.update(model.getUsersIn(list.getSelectedValue().getBuilding()));
    }
}
