package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.OutsideGreenLightIndicator;
import com.INFO801.TP_INFO801.application.model.OutsideRedLightIndicator;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class EntryLightsContainer extends JPanel implements Observer {
    private OutsideGreenLightIndicator ogli;
    private OutsideRedLightIndicator orli;
    private JLabel gl;
    private JLabel rl;

    public EntryLightsContainer(OutsideGreenLightIndicator ogli, OutsideRedLightIndicator orli){
        this.ogli = ogli;
        this.orli = orli;
        this.ogli.addObserver(this);
        this.orli.addObserver(this);
        this.gl = new JLabel(Constants.LIGHT);
        this.gl.setForeground(Constants.GREEN_OFF_COLOR);
        this.rl = new JLabel(Constants.LIGHT);
        this.rl.setForeground(Constants.RED_OFF_COLOR);
        this.add(gl);
        this.add(rl);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(ogli.on){
            this.gl.setForeground(Constants.GREEN_ON_COLOR);
        }else{
            this.gl.setForeground(Constants.GREEN_OFF_COLOR);
        }

        if(orli.on){
            this.gl.setForeground(Constants.RED_ON_COLOR);
        }else{
            this.gl.setForeground(Constants.RED_OFF_COLOR);
        }
    }
}
