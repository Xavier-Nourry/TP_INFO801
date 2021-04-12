package com.INFO801.TP_INFO801.application.model;

import org.jspace.RemoteSpace;

public interface Agent {
    String tsServerURI = null;
    RemoteSpace server = null;
    String id = null;

    RemoteSpace tsServerConnection();
    void manageState() throws InterruptedException;
}
