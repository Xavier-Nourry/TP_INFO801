package com.INFO801.TP_INFO801.application;

import com.INFO801.TP_INFO801.application.model.Door;
import com.INFO801.TP_INFO801.application.model.Infrastructure;

public class App {

    public static void main(String[] argv){
        // TODO : nombre de buildings, portes et adresse serveur à déterminer avant le lancement de la vue
        new Infrastructure(2, 2).runAll();
    }

}