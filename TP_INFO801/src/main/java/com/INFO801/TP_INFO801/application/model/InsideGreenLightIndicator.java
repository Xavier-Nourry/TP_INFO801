package com.INFO801.TP_INFO801.application.model;

import com.INFO801.TP_INFO801.access_system.TupleSpace;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import static java.lang.System.exit;

public class InsideGreenLightIndicator extends Observable implements Agent, Runnable, LightIndicator {
    private final String id;
    private final RemoteSpace server;
    private boolean on;
    private Color color;

    public InsideGreenLightIndicator(String id){
        this.id = id + " - Internal Reader - Green Light";
        this.server = tsServerConnection();
        this.on = false;
        this.color = Color.GREEN;
    }

    @Override
    public void run() {
        while(true) {
            try {
                manageState();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public RemoteSpace tsServerConnection(){
        System.out.println("Connexion de "+ id + " à " + TupleSpace.CLIENT_URI + "...");
        RemoteSpace server = null;
        try {
            server = new RemoteSpace(TupleSpace.CLIENT_URI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(server == null){
            System.err.println("Erreur lors de la connexion au serveur d'espace de tuples");
            exit(1);
        }else{
            System.out.println("Connexion au serveur d'espace de tuples réussie");
        }
        return server;
    }

    public void manageState() throws InterruptedException {
        Object[] response;
        response = server.get(new ActualField(id), new FormalField(Boolean.class));
        if(response != null){
            on = (boolean) response[1];
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}