package com.INFO801.TP_INFO801.application.model;

public class Door implements Runnable{
    private int id;

    public Door(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Porte "+ id + " en fonction");
    }
}
