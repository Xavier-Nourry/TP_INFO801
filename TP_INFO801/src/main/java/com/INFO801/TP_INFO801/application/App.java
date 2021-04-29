package com.INFO801.TP_INFO801.application;

import com.INFO801.TP_INFO801.application.model.Infrastructure;
import com.INFO801.TP_INFO801.application.view.MainFrame;

public class App {

    public static void main(String[] argv){
        new MainFrame(new Infrastructure());
    }

}