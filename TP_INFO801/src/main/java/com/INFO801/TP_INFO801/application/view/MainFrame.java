package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.Building;
import com.INFO801.TP_INFO801.application.model.Infrastructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame extends JFrame implements UIComponent{
    private final Infrastructure infra;
    private final JPanel mainPanel;

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
        this.mainPanel.setLayout(new GridLayout(this.infra.buildings.size(), 1)); // Calé à gauche
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
