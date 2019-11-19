package Editor.AddEvents;

import javax.swing.*;
import java.awt.*;

public class AddEventsHome {
    private JFrame frame;

    public AddEventsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        JPanel pane = new JPanel(grid);
        frame = new JFrame();

        JButton addCollisionEvent = new JButton("Add A Collision Event");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addCollisionEvent, c);

        JButton addProjectileEvent = new JButton("Add Projectile Event");
        c.gridy = 1;
        pane.add(addProjectileEvent, c);

        JButton addGameEndCase = new JButton("Add Game End Condition");
        c.gridy = 2;
        pane.add(addGameEndCase, c);

        addCollisionEvent.addActionListener(e -> new AddCollision().setVisible());
        addProjectileEvent.addActionListener(e -> new AddProjectile().setVisible());
        addGameEndCase.addActionListener(e -> new AddGameEnd().setVisible());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}