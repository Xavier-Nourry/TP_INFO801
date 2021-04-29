package com.INFO801.TP_INFO801.access_system;

public class BuildingComplex {
    public static void main(String[] args){
        // On crée deux bâtiments dans le complexes, et on met en route leur système de contrôle d'accès
        Thread building1 = new Thread(new Building("Building1", 2));
        building1.start();

        Thread building2 = new Thread(new Building("Building2", 2));
        building2.start();
    }
}
