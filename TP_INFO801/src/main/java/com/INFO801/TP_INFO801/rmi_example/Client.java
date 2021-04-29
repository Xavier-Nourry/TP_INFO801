package com.INFO801.TP_INFO801.rmi_example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Client(){}

    public static void main(String[] args){
        try{
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
            PassManager manager = (PassManager) reg.lookup("PassManager");
            System.out.println("[Client] "+manager.sayHello());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
