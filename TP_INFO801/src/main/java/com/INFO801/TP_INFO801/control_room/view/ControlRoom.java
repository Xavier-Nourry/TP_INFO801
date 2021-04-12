package com.INFO801.TP_INFO801.control_room.view;
import com.INFO801.TP_INFO801.control_room.controller.CreatePassListener;
import com.INFO801.TP_INFO801.control_room.model.DataClient;
import com.INFO801.TP_INFO801.control_room.model.PassListModel;

import javax.swing.*;
import java.awt.*;

public class ControlRoom extends JFrame{
    private DataClient model;

    public static void main(String[] args) {
        JFrame frame = new ControlRoom();
    }

    public ControlRoom() {
        super("INFO801 - Salle de contrôle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DataClient();

        Container pane = getContentPane();
        setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.add(passPanel());
        pane.add(new JSeparator());
        pane.add(buildingPanel());
        pane.add(new JSeparator());
        pane.add(logPanel());

        // on demande d'attribuer une taille minimale à la fenêtre
        //  (juste assez pour voir tous les composants)
        pack();
        // on centre la fenêtre
        setLocationRelativeTo(null);
        // on rend la fenêtre visible
        setVisible(true);
    }

    private JPanel passPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // List panel
        PassListModel listModel = new PassListModel(model.getPasses());
        JList<PassInfo> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        
        // Managing panel
        JPanel manager = new JPanel();
        BoxLayout managerLayout = new BoxLayout(manager, BoxLayout.PAGE_AXIS);
        manager.setLayout(managerLayout);

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextArea authField = new JTextArea();

        JButton acceptButton = new JButton("Créer le badge");
        acceptButton.addActionListener(new CreatePassListener(firstNameField, lastNameField, authField));
        JButton deleteButton = new JButton("Supprimer");

        manager.add(deleteButton);
        manager.add(Box.createRigidArea(new Dimension(0, 5)));
        manager.add(new JSeparator());
        manager.add(new JLabel("Prénom"));
        manager.add(firstNameField);
        manager.add(new JLabel("Nom"));
        manager.add(lastNameField);
        manager.add(new JLabel("Accès"));
        manager.add(authField);
        manager.add(Box.createRigidArea(new Dimension(0, 5)));
        manager.add(acceptButton);

        panel.add(list, BorderLayout.CENTER);
        panel.add(manager, BorderLayout.PAGE_END);

        return panel;
    }

    private JPanel buildingPanel(){
        JPanel panel = new JPanel();
        panel.add(new Label("buildings"));
        return panel;
    }

    private JPanel logPanel(){
        JPanel panel = new JPanel();
        panel.add(new Label("LOGS"));
        return panel;
    }
}
