package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.InsideGreenLightIndicator;
import com.INFO801.TP_INFO801.application.model.InsideRedLightIndicator;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ExitLightsContainer extends JPanel implements Observer {
    private final InsideGreenLightIndicator igli;
    private final InsideRedLightIndicator irli;
    private final JLabel gl;
    private final JLabel rl;

    public ExitLightsContainer(InsideGreenLightIndicator igli, InsideRedLightIndicator irli){
        this.igli = igli;
        this.irli = irli;
        this.igli.addObserver(this);
        this.irli.addObserver(this);
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
        if(igli.on){
            this.gl.setText(Constants.LIGHT_ON);
            this.gl.setForeground(Constants.GREEN_ON_COLOR);
        }else{
            this.gl.setText(Constants.LIGHT_OFF);
            this.gl.setForeground(Constants.GREEN_OFF_COLOR);
        }

        if(irli.on){
            this.rl.setText(Constants.LIGHT_ON);
            this.rl.setForeground(Constants.RED_ON_COLOR);
        }else{
            this.rl.setText(Constants.LIGHT_OFF);
            this.rl.setForeground(Constants.RED_OFF_COLOR);
        }
    }
}
