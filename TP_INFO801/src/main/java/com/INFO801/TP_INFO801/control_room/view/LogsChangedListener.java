package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.LogEntry;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogsChangedListener implements PropertyChangeListener{
    private final LogListModel logList;

    public LogsChangedListener(LogListModel logList) {
        this.logList = logList;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("logs")){
            String[] newValue = (String[])evt.getNewValue();
            logList.update(newValue);
        }
    }
}
