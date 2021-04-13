package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;

public class BuildingListModel extends AbstractListModel<Building> {
    private Building[] model;

    public BuildingListModel(PassManagerClient client) {
        model = client.getBuildings();
    }

    @Override
    public int getSize() {
        return model.length;
    }

    @Override
    public Building getElementAt(int index) {
        return model[index];
    }
}
