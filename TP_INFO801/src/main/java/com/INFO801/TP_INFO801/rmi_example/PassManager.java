package com.INFO801.TP_INFO801.rmi_example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PassManager extends Remote {
    String sayHello() throws RemoteException;
}
