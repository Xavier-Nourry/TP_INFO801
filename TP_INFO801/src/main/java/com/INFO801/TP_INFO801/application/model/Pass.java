package com.INFO801.TP_INFO801.application.model;

public class Pass {
    private int id;
    private String name;

    public Pass(int id){
        this.id = id;
        this.name = randomName();
    }

    //TODO : utiliser lib de génération de noms random
    private String randomName() {
        System.out.println("Nom random à implémenter");
        return "";
    }


}
