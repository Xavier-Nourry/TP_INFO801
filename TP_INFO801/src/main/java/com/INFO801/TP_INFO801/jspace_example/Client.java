package com.INFO801.TP_INFO801.jspace_example;

import org.jspace.RemoteSpace;

import java.io.IOException;

public class Client {
    public static void main(String[] args){
        try {
            String uri = "tcp://127.0.0.1:9001/ts?keep";
            // Connect to the remote chat space
            System.out.println("Connecting to server " + uri + "...");
            RemoteSpace ts = new RemoteSpace(uri);
            System.out.println("Client connected");
            ts.put("Hello", "World");
            ts.put("Goodbye", "World");
            System.out.println("Tuples put");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
