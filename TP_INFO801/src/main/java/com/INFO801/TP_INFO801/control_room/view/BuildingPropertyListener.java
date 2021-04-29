package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuildingPropertyListener implements PropertyChangeListener {
    private final BuildingListModel list;

    public BuildingPropertyListener(BuildingListModel list){
        this.list = list;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("buildings")){
            list.update((Building[])evt.getNewValue());
        }
    }
}
