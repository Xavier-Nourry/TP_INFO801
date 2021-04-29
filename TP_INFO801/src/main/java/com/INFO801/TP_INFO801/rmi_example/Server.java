package com.INFO801.TP_INFO801.rmi_example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements PassManager {

    public Server(){}

    @Override
    public String sayHello() throws RemoteException {
        return "Hello world!";
    }

    public static void main(String[] args){
        try{
            Server serv = new Server();
            PassManager stub = (PassManager) UnicastRemoteObject.exportObject(serv, 0);
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.bind("PassManager", stub);
            System.out.println("Server ready");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
