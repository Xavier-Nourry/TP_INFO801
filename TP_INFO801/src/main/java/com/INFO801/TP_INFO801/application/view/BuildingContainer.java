package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.Building;
import com.INFO801.TP_INFO801.application.model.Door;

import javax.swing.*;
import java.awt.*;

public class BuildingContainer extends JPanel implements UIComponent{
    private Building building;

    public BuildingContainer(Building buidling){
        this.building = buidling;
        this.setLayout(new GridLayout(3, 1));
        fillUIFromModel();
    }

    @Override
    public void fillUIFromModel() {
        this.add(new JLabel(this.building.id));
        //this.add(setListLegend());
        JPanel doorsPanel = new JPanel(new GridLayout(this.building.doors.length, 1));
        for(Door door : this.building.doors){
            doorsPanel.add(new DoorContainer(door));
        }
        this.add(doorsPanel);
        // TODO : gérer les alarmes
    }

    /*private JPanel setListLegend(){
        JPanel res = new JPanel(new GridLayout(1, 5));
        JLabel door = new JLabel("Porte");
        JLabel state = new JLabel("Etat");
        JLabel entryBadger = new JLabel("Badgeuse Entrée");
        JLabel exitBadger = new JLabel("Badgeuse Sortie");
        JLabel passMenu = new JLabel("Badger");
        res.add(door);
        res.add(state);
        res.add(entryBadger);
        res.add(exitBadger);
        res.add(passMenu);
        return res;
    }*/
}
