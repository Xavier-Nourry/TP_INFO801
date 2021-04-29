package com.INFO801.TP_INFO801.application.model;

import org.jspace.RemoteSpace;

public interface Agent {
    RemoteSpace tsServerConnection();
    void manageState() throws InterruptedException;
}
