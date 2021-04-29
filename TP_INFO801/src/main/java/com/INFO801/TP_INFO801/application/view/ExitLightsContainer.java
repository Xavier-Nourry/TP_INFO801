package com.INFO801.TP_INFO801.application.view;

import com.INFO801.TP_INFO801.application.model.InsideGreenLightIndicator;
import com.INFO801.TP_INFO801.application.model.InsideRedLightIndicator;
import com.INFO801.TP_INFO801.application.model.OutsideGreenLightIndicator;
import com.INFO801.TP_INFO801.application.model.OutsideRedLightIndicator;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ExitLightsContainer extends JPanel implements Observer {
    private InsideGreenLightIndicator igli;
    private InsideRedLightIndicator irli;
    private JLabel gl;
    private JLabel rl;

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
