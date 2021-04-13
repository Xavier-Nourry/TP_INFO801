package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuildingUserListChangedListener implements PropertyChangeListener {

    private Building building;
    private PassListModel list;

    public BuildingUserListChangedListener(Building building, PassListModel listModel) {
        this.building = building;
        this.list = listModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(building.id)){
            list.update((Pass[])evt.getNewValue());
        }
    }
}
