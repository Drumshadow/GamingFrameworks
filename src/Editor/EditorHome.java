package Editor;

import Editor.AddEvents.AddEventsHome;
import Editor.AddHUDElements.AddHUDElementsHome;
import Editor.AddInputs.AddInputsHome;
import Editor.AddObjects.AddObjectsHome;
import Editor.AddOptions.AddOptionsHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addInputsButton;
    private JButton addHUDElementsButton;
    private JButton addObjectButton;
    private JButton addOptionsButton;
    private JButton addEventsButton;

    public EditorHome() {
        frame = new JFrame("Slammin' Game Editor");

        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        addObjectButton = new JButton("Add Object (Like the player, walls, " +
                "floor, enemies, and etc)");
        c.insets = new Insets(20, 0, 20, 0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(addObjectButton, c);

        addInputsButton = new JButton("Add Inputs (For the playable " +
                "character)");
        c.gridy = 1;
        pane.add(addInputsButton, c);

        addEventsButton = new JButton("Add Event");
        c.gridy = 2;
        pane.add(addEventsButton, c);

        addHUDElementsButton = new JButton("Add HUD Element");
        c.gridy = 3;
        pane.add(addHUDElementsButton, c);

        addOptionsButton = new JButton("Add Options");
        c.gridy = 4;
        pane.add(addOptionsButton, c);

        pane.setSize(-1, -1);

        addHUDElementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddHUDElementsHome().setVisible();
            }
        });

        addInputsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddInputsHome().setVisible();
            }
        });

        addOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddOptionsHome().setVisible();
            }
        });

        addEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEventsHome().setVisible();
            }
        });

        addObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddObjectsHome().setVisible();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
