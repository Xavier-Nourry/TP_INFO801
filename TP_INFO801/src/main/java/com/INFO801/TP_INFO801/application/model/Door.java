package com.INFO801.TP_INFO801.application.model;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.Observable;

import static java.lang.System.exit;

public class Door extends Observable implements Agent, Runnable{
    private String tsServerURI;
    private RemoteSpace server;
    private InsideGreenLightIndicator igli; // Inside
    private InsideRedLightIndicator irli; // Inside
    private OutsideGreenLightIndicator ogli; // Outside
    private OutsideRedLightIndicator orli; // Outside
    private String id;
    private boolean open;

    public Door(String buildingID, int id, String tsServerURI){
        this.tsServerURI = tsServerURI;
        this.server = tsServerConnection();
        this.id = buildingID + " - Door"+ id;
        this.igli = new InsideGreenLightIndicator(this.id, this.tsServerURI);
        this.irli = new InsideRedLightIndicator(this.id, this.tsServerURI);
        this.ogli = new OutsideGreenLightIndicator(this.id, this.tsServerURI);
        this.orli = new OutsideRedLightIndicator(this.id, this.tsServerURI);
        this.open = false;
    }

    @Override
    public void run() {
        //Lancement des threads des voyants
        new Thread(igli).start();
        new Thread(irli).start();
        new Thread(ogli).start();
        new Thread(orli).start();

        // Traitement
        while(true){
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
            open = (boolean) response[1];
            setChanged();
            notifyObservers();
        }
    }

    // A appeler depuis l'UI pour passer une porte
    private void crossing(){
        try {
            server.put(new ActualField(id + " - Laser"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // A appeler depuis l'UI pour badger depuis l'intérieur
    private void badgingFromInside(String passID){
        try {
            server.put(new ActualField(id + " - Internal Reader"), new ActualField(passID));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // A appeler depuis l'UI pour badger de l'extérieur
    private void badgingFromOutside(String passID){
        try {
            server.put(new ActualField(id + " - External Reader"), new ActualField(passID));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
