package com.INFO801.TP_INFO801.control_room;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ControlRoom extends JFrame{

    public static void main(String[] args) {
        JFrame frame = new ControlRoom();
    }

    public ControlRoom() {
        super("titre de l'application");
        // la fenêtre doit se fermer quand on clique sur la croix rouge
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // on ajoute le texte "Hello, World!" dans la fenêtre
        getContentPane().add(new JLabel("Hello, World!"));

        // on demande d'attribuer une taille minimale à la fenêtre
        //  (juste assez pour voir tous les composants)
        pack();
        // on centre la fenêtre
        setLocationRelativeTo(null);
        // on rend la fenêtre visible
        setVisible(true);
    }
}
