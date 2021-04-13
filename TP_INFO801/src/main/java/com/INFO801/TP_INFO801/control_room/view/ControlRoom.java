package com.INFO801.TP_INFO801.control_room.view;
import com.INFO801.TP_INFO801.control_room.controller.CreatePassListener;
import com.INFO801.TP_INFO801.control_room.controller.DeletePassListener;
import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.database_server.Building;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.awt.*;

public class ControlRoom extends JFrame{
    private final PassManagerClient model;

    public static void main(String[] args) {
        JFrame frame = new ControlRoom();
    }

    public ControlRoom() {
        super("INFO801 - Salle de contrôle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new PassManagerClient();

        Container pane = getContentPane();
        setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.add(passPanel());
        // TODO separator
        pane.add(buildingPanel(null));
        // TODO separator
        pane.add(logPanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel passPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // List panel
        PassListModel listModel = new PassListModel(model.getPasses());
        // subscribe the model to a property listener in the model
        model.addPropertyChangeListener(new PassPropertyListener(listModel));

        JList<PassInfo> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        // Managing panel
        JPanel manager = new JPanel();
        BoxLayout managerLayout = new BoxLayout(manager, BoxLayout.PAGE_AXIS);
        manager.setLayout(managerLayout);

        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextArea authField = new JTextArea();

        JButton acceptButton = new JButton("Créer le badge");
        acceptButton.addActionListener(new CreatePassListener(model, idField, firstNameField, lastNameField, authField));
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new DeletePassListener(model, list));

        manager.add(deleteButton);
        manager.add(Box.createRigidArea(new Dimension(0, 5)));
        manager.add(new JSeparator());
        manager.add(new JLabel("ID"));
        manager.add(idField);
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

    private JPanel buildingPanel(Building building){
        JPanel panel = new JPanel();
        BorderLayout layout = new BorderLayout();
        panel.setLayout(layout);

        JPanel buildingList = buildingListPanel();
        JPanel userList = userListPanel(building);

        panel.add(buildingList, BorderLayout.LINE_START);
        panel.add(userList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildingListPanel(){
        JPanel buildingListPanel = new JPanel();
        BoxLayout listLayout = new BoxLayout(buildingListPanel, BoxLayout.PAGE_AXIS);
        buildingListPanel.setLayout(listLayout);

        // List panel
        BuildingListModel listModel = new BuildingListModel(model.getBuildings());
        // subscribe the model to a property listener in the model
        model.addPropertyChangeListener(new BuildingPropertyListener(listModel));

        JList<BuildingInfo> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        buildingListPanel.add(list);
        return buildingListPanel;
    }

    private JPanel userListPanel(Building building){

        JPanel userListPanel = new JPanel();
        if(building == null){
            userListPanel.add(new Label("Pas de bâtiment sélectionné."));
        } else {
            PassListModel listModel = new PassListModel(model.getUsersIn(building));
            JList<PassInfo> userList = new JList<>(listModel);
            model.addPropertyChangeListener(new BuildingUserListChangedListener(building, listModel));
            userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            userList.setLayoutOrientation(JList.VERTICAL);
            userListPanel.add(userList);
        }
        return userListPanel;
    }

    private JPanel logPanel(){
        JPanel panel = new JPanel();
        panel.add(new Label("LOGS"));
        return panel;
    }
}
