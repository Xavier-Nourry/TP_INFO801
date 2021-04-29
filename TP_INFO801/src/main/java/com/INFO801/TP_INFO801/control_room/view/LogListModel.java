package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.LogEntry;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class LogListModel extends AbstractListModel<String> {
    private String[] logs;

    public LogListModel(String[] logs) {
        setModel(logs);
    }

    private void setModel(String[] model){
        this.logs = model;
    }

    @Override
    public int getSize() {
        return logs.length;
    }

    @Override
    public String getElementAt(int index) {
        return logs[index];
    }

    public void update(String[] content){
        setModel(content);
        fireContentsChanged(this, 0, content.length);
    }
}
