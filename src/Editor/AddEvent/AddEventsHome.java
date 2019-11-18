package Editor.AddEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addCollisionEvent;
    private JButton addProjectileEvent;
    private JButton addGameEndCase;

    public AddEventsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        addCollisionEvent = new JButton("Add A Collision Event");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addCollisionEvent, c);

        addProjectileEvent = new JButton("Add Projectile Event");
        c.gridy = 1;
        pane.add(addProjectileEvent, c);

        addGameEndCase = new JButton("Add Game End Condition");
        c.gridy = 2;
        pane.add(addGameEndCase, c);

        addCollisionEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCollision().setVisible();
            }
        });

        addProjectileEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        addGameEndCase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddGameEnd().setVisible();
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
