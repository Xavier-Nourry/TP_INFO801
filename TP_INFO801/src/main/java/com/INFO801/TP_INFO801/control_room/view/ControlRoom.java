package com.INFO801.TP_INFO801.control_room.view;
import com.INFO801.TP_INFO801.control_room.controller.BuildingSelectedListener;
import com.INFO801.TP_INFO801.control_room.controller.CreatePassListener;
import com.INFO801.TP_INFO801.control_room.controller.DeletePassListener;
import com.INFO801.TP_INFO801.control_room.model.PassManagerClient;
import com.INFO801.TP_INFO801.database_server.Pass;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ControlRoom extends JFrame{
    private PassManagerClient model;
    private PassListModel userListModel;
    private JList<BuildingInfo> buildingList;

    public static void main(String[] args) {
        JFrame frame = new ControlRoom();
    }

    public ControlRoom() {
        super("INFO801 - Salle de contrôle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            model = new PassManagerClient();
            System.out.println("Connecté au serveur avec succès.");

            Container pane = getContentPane();
            setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
            pane.add(passPanel());
            pane.add(new JSeparator(SwingConstants.VERTICAL));
            pane.add(new JSeparator(SwingConstants.VERTICAL));
            pane.add(buildingPanel());
            pane.add(new JSeparator(SwingConstants.VERTICAL));
            pane.add(new JSeparator(SwingConstants.VERTICAL));
            pane.add(logPanel());

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Impossible de se connecter au serveur.");
        }
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

        panel.add(new JLabel("Badges"), BorderLayout.PAGE_START);
        panel.add(list, BorderLayout.CENTER);
        panel.add(manager, BorderLayout.PAGE_END);

        return panel;
    }

    private JPanel buildingPanel(){
        JPanel userListParentPanel = new JPanel();
        BorderLayout layout = new BorderLayout();
        userListParentPanel.setLayout(layout);

        // needed for selected building callbacks
        userListModel = new PassListModel(new Pass[0]);
        JPanel buildingList = buildingListPanel();
        JPanel userListPanel = userListPanel();

        userListParentPanel.add(new JLabel("Bâtiments"), BorderLayout.PAGE_START);
        userListParentPanel.add(buildingList, BorderLayout.LINE_START);
        userListParentPanel.add(userListPanel, BorderLayout.CENTER);

        return userListParentPanel;
    }

    private JPanel buildingListPanel(){
        JPanel buildingListPanel = new JPanel();
        BoxLayout listLayout = new BoxLayout(buildingListPanel, BoxLayout.PAGE_AXIS);
        buildingListPanel.setLayout(listLayout);

        // List panel
        BuildingListModel listModel = new BuildingListModel(model.getBuildings());
        // subscribe the model to a property listener in the model
        model.addPropertyChangeListener(new BuildingPropertyListener(listModel));

        buildingList = new JList<>(listModel);
        buildingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        buildingList.setLayoutOrientation(JList.VERTICAL);

        buildingList.addListSelectionListener(new BuildingSelectedListener(model, userListModel, buildingList));

        buildingListPanel.add(buildingList);
        return buildingListPanel;
    }

    private JPanel userListPanel(){
        JPanel userListPanel = new JPanel();
        JList<PassInfo> userList = new JList<>(userListModel);
        model.addPropertyChangeListener(new BuildingUserListChangedListener(model,userListModel, buildingList));
        userList.setLayoutOrientation(JList.VERTICAL);
        userListPanel.add(userList);
        return userListPanel;
    }

    private JPanel logPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        LogListModel listModel = new LogListModel(model.getLogsAsString());
        // subscribe the model to a property listener in the model

        // property change listener
        JList<String> list = new JList<>(listModel);
        model.addPropertyChangeListener(new LogsChangedListener(listModel));

        list.setLayoutOrientation(JList.VERTICAL);

        panel.add(new JLabel("Journal de bord"), BorderLayout.PAGE_START);
        panel.add(list, BorderLayout.CENTER);

        return panel;
    }
}
