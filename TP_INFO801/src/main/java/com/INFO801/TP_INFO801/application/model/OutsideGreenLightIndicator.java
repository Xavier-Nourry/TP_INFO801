package com.INFO801.TP_INFO801.application.model;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.Observable;

import static java.lang.System.exit;

public class OutsideGreenLightIndicator extends Observable implements Agent, Runnable{
    private String id;
    private String tsServerURI;
    private RemoteSpace server;
    private boolean on;

    public OutsideGreenLightIndicator(String id, String tsServerURI){
        this.id = id + " - External Reader - Green Light";
        this.tsServerURI = tsServerURI;
        this.server = tsServerConnection();
        this.on = false;
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
        System.out.println("Connexion de "+ id + " à " + tsServerURI + "...");
        RemoteSpace server = null;
        try {
            server = new RemoteSpace(tsServerURI);
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
        response = server.get(new ActualField(id), new FormalField(boolean.class));
        if(response != null){
            on = (boolean) response[1];
            setChanged();
            notifyObservers();
        }
    }
}