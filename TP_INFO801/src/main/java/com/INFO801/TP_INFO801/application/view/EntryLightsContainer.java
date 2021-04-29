package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.OutsideGreenLightIndicator;
import com.INFO801.TP_INFO801.application.model.OutsideRedLightIndicator;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class EntryLightsContainer extends JPanel implements Observer {
    private final OutsideGreenLightIndicator ogli;
    private final OutsideRedLightIndicator orli;
    private final JLabel gl;
    private final JLabel rl;

    public EntryLightsContainer(OutsideGreenLightIndicator ogli, OutsideRedLightIndicator orli){
        this.ogli = ogli;
        this.orli = orli;
        this.ogli.addObserver(this);
        this.orli.addObserver(this);
        this.gl = new JLabel(Constants.LIGHT_OFF);
        this.gl.setForeground(Constants.GREEN_OFF_COLOR);
        this.rl = new JLabel(Constants.LIGHT_OFF);
        this.rl.setForeground(Constants.RED_OFF_COLOR);
        this.add(gl);
        this.add(rl);
    }

    // TODO : fonction de maj des LightsContainer doit être commune
    @Override
    public void update(Observable o, Object arg) {
        if(ogli.on){
            this.gl.setText(Constants.LIGHT_ON);
            this.gl.setForeground(Constants.GREEN_ON_COLOR);
        }else{
            this.gl.setText(Constants.LIGHT_OFF);
            this.gl.setForeground(Constants.GREEN_OFF_COLOR);
        }

        if(orli.on){
            this.rl.setText(Constants.LIGHT_ON);
            this.rl.setForeground(Constants.RED_ON_COLOR);
        }else{
            this.rl.setText(Constants.LIGHT_OFF);
            this.rl.setForeground(Constants.RED_OFF_COLOR);
        }
    }
}
