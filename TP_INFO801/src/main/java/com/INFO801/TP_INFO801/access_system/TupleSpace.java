package com.INFO801.TP_INFO801.access_system;

import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TupleSpace {
    public final static String SERVER_URI = "tcp://127.0.0.1:9002/?keep";
    public final static String CLIENT_URI = "tcp://127.0.0.1:9002/ts?keep";

    public static RemoteSpace remoteSpaceConnexion(String elementName){
        try {
            RemoteSpace ts = new RemoteSpace(TupleSpace.CLIENT_URI);
            System.out.println(elementName + " connected");
            return ts;
        } catch (IOException e) {
            System.out.println(elementName + "didn't connect to tuple space");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SpaceRepository repository = new SpaceRepository();
        SequentialSpace ts = new SequentialSpace();
        repository.add("ts", ts);

        URI uri;
        try {
            // On expose un accès à l'espace de tuples
            uri = new URI(SERVER_URI);
        } catch (URISyntaxException e) {
            System.out.println("Error with tuple space URI");
            e.printStackTrace();
            return;
        }

        String gateUri = "tcp://" + uri.getHost() + ":" + uri.getPort() +  "?keep" ;
        repository.addGate(gateUri);
        System.out.println("Tuple space ready");

        while (true);
    }

}
