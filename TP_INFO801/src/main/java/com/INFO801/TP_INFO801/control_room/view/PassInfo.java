package com.INFO801.TP_INFO801.control_room.view;

import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.awt.*;

public class PassInfo {
    private final Pass data;

    public Pass getData() {
        return data;
    }

    public PassInfo(Pass data) {
        this.data = data;
    }

    public String toString(){
        return "("+data.id+") "+data.firstName+" "+data.lastName;
    }
}
