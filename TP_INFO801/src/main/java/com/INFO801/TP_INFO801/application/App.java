package com.INFO801.TP_INFO801.application;

import com.INFO801.TP_INFO801.application.model.Infrastructure;

public class App {

    public static void main(String[] argv) throws InterruptedException {
        // TODO : nombre de buildings et de portes à déterminer avant le lancement de l'app
        Infrastructure infra = new Infrastructure(2, 2, 15);
        infra.runAll();
    }

}