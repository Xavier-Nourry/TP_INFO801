package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;

public class BuildingListModel extends AbstractListModel<BuildingInfo> {
    private Building[] model;

    private void setModel(Building[] data){
        this.model = data;
        Arrays.sort(model, Comparator.comparing(o -> o.id));
    }

    public BuildingListModel(Building[] data) {
        setModel(data);
    }

    @Override
    public int getSize() {
        return model.length;
    }

    @Override
    public BuildingInfo getElementAt(int index) {
        return new BuildingInfo(model[index]);
    }

    public void update(Building[] content) {
        setModel(content);
        fireContentsChanged(this, 0, content.length);
    }
}
