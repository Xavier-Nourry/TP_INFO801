package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.controller.ReadPassListener;
import com.INFO801.TP_INFO801.application.model.Door;
import javax.swing.*;
import java.awt.*;

public class PassMenuContainer extends JPanel{

    public PassMenuContainer(Door door){
        this.setLayout(new GridLayout(3, 1));
        JComboBox<String> badgerChoice = new JComboBox<>(Constants.PASS_READER_CHOICES);
        JTextField passID = new JTextField();
        JButton confirm = new JButton(Constants.CONFIRM);
        confirm.addActionListener(new ReadPassListener(door, passID, badgerChoice));

        this.add(badgerChoice);
        this.add(passID);
        this.add(confirm);
    }


}