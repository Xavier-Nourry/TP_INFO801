package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.controller.ReadPassListener;
import com.INFO801.TP_INFO801.application.model.Door;
import javax.swing.*;
import java.awt.*;

public class PassMenuContainer extends JPanel{
    private Door door;
    private JComboBox<String> badgerChoice;
    private JTextField passID;
    private JButton confirm;

    public PassMenuContainer(Door door){
        this.door = door;
        //this.door.addObserver(this);
        this.setLayout(new GridLayout(3, 1));
        this.badgerChoice = new JComboBox<>(Constants.PASS_READER_CHOICES);
        this.passID = new JTextField();
        this.confirm = new JButton(Constants.CONFIRM);
        this.confirm.addActionListener(new ReadPassListener(this.door, this.passID, this.badgerChoice));

        this.add(this.badgerChoice);
        this.add(this.passID);
        this.add(this.confirm);
    }


}