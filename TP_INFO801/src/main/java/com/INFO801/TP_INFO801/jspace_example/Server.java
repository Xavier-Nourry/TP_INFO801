package com.INFO801.TP_INFO801.jspace_example;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

import java.net.URI;
import java.net.URISyntaxException;

public class Server {
    public static void main(String[] args) {
        try {
            SpaceRepository repository = new SpaceRepository();
            SequentialSpace ts = new SequentialSpace();
            repository.add("ts", ts);
            String uri = "tcp://127.0.0.1:9001/?keep";

            // Open a gate
            URI myUri = new URI(uri);
            String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "?keep" ;
            repository.addGate(gateUri);
            System.out.println("Server ready");
            Object[] t = ts.get(new FormalField(String.class), new FormalField(String.class));
            System.out.println(t[0] + " " + t[1]);
            Object[] t2 = ts.get(new FormalField(String.class), new ActualField("World"));
            System.out.println(t2[0] + " " + t2[1]);


        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
