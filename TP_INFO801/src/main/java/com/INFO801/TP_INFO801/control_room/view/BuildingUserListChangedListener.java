package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuildingUserListChangedListener implements PropertyChangeListener {

    private final PassListModel list;

    public BuildingUserListChangedListener(PassListModel listModel) {
        this.list = listModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        list.update((Pass[])evt.getNewValue());
    }
}
