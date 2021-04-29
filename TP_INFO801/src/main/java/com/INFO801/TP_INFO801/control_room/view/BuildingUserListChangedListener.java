package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuildingUserListChangedListener implements PropertyChangeListener {
    private final PassManagerClient client;
    private final PassListModel list;
    private final JList<BuildingInfo> buildingList;

    public BuildingUserListChangedListener(PassManagerClient client, PassListModel listModel, JList<BuildingInfo> buildingList) {
        this.client = client;
        this.list = listModel;
        this.buildingList = buildingList;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("users")){
            BuildingInfo selectedValue = buildingList.getSelectedValue();
            if(selectedValue==null) return;
            Building building = selectedValue.getBuilding();
            Pass[] users = client.getUsersIn(building);
            list.update(users);
        }
    }
}
