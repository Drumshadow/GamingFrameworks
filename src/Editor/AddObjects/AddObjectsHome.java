package Editor.AddObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddObjectsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addAIButton;
    private JButton addCopyButton;
    private JButton addNewObject;

    public AddObjectsHome() {
        GridBagLayout grid = new GridBagLayout();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        pane = new JPanel(grid);
        frame = new JFrame();

        addAIButton = new JButton("Add AI Object (Enemy player, moving blocks" +
                ", etc)");
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(20, 0, 20, 0);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(addAIButton, c);

        addCopyButton = new JButton("Copy an Object (Easy duplication)");
        c.gridy = 1;
        pane.add(addCopyButton, c);

        addNewObject = new JButton("Create New Object");
        c.gridy = 2;
        pane.add(addNewObject, c);

        addAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddAI().setVisible();
            }
        });

        addCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCopy().setVisible();
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
