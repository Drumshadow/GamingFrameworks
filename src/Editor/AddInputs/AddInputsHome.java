package Editor.AddInputs;

import javax.swing.*;
import java.awt.*;

public class AddInputsHome {
    private JFrame frame;

    public AddInputsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        JPanel pane = new JPanel(grid);
        frame = new JFrame();

        JButton addMoveButton = new JButton("Add Button for Movement");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addMoveButton, c);

        JButton addPlaySoundButton = new JButton("Add Button for Sound");
        c.gridy = 1;
        pane.add(addPlaySoundButton, c);

        JButton addProjectileButton = new JButton("Add Button for Firing Projectile");
        c.gridy = 2;
        pane.add(addProjectileButton, c);

        addMoveButton.addActionListener(e -> new AddMove().setVisible());
        addPlaySoundButton.addActionListener(e -> new AddSound().setVisible());
        addProjectileButton.addActionListener(e -> new AddProjectileButton().setVisible());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}