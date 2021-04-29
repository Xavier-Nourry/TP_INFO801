package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.Building;
import com.INFO801.TP_INFO801.application.model.Infrastructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements UIComponent{
    private Infrastructure infra;
    private JPanel mainPanel;

    public MainFrame(Infrastructure infra) {
        super("TP INFO801");

        this.infra = infra;

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(l);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(this.infra.buildings.length, 1)); // Calé à gauche
        fillUIFromModel();

        this.infra.runAll(); // Lancement de tous les threads de l'infra

        setContentPane(mainPanel);
        setSize(1600, 900);
        setVisible(true);
    }

    public void fillUIFromModel() {
        for(Building building : this.infra.buildings){
            mainPanel.add(new BuildingContainer(building));
        }
    }
}
