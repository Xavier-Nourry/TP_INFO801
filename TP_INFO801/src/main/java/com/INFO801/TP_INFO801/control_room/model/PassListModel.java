package com.INFO801.TP_INFO801.control_room.model;

import com.INFO801.TP_INFO801.control_room.view.PassInfo;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;

public class PassListModel extends AbstractListModel<PassInfo> {
    private final Pass[] model;

    public PassListModel(Pass[] passes) {
        model = passes;
    }

    @Override
    public int getSize() {
        return model.length;
    }

    @Override
    public PassInfo getElementAt(int index) {
        return new PassInfo(model[index]);
    }
}
