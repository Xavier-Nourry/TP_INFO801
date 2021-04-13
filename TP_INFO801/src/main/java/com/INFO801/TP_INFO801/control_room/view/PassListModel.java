package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.control_room.view.PassInfo;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class PassListModel extends AbstractListModel<PassInfo>  {
    private Pass[] model;

    public PassListModel(Pass[] data) {
        model = data;
    }

    @Override
    public int getSize() {
        return model.length;
    }

    @Override
    public PassInfo getElementAt(int index) {
        return new PassInfo(model[index]);
    }

    public void update(Pass[] content) {
        model = content;
        fireContentsChanged(this, 0, content.length);
    }
}
