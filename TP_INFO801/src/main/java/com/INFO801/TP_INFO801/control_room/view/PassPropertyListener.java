package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PassPropertyListener implements PropertyChangeListener {

    private final DefaultListModel<PassInfo> list;

    public PassPropertyListener(DefaultListModel<PassInfo> list){
        this.list = list;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("passes")){
            List<PassInfo> content = Arrays.stream(((Pass[])evt.getNewValue())).map(PassInfo::new).collect(Collectors.toList());
            list.clear();
            list.addAll(content);
        }
    }
}
