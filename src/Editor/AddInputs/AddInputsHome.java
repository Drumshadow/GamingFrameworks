package Editor.AddInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInputsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addMoveButton;
    private JButton addPlaySoundButton;

    public AddInputsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        addMoveButton = new JButton("Add Button for Movement");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addMoveButton, c);

        addPlaySoundButton = new JButton("Add Button for Sound");
        c.gridy = 1;
        pane.add(addPlaySoundButton, c);

        addMoveButton.addActionListener(e -> new AddMove().setVisible());

        addPlaySoundButton.addActionListener(e -> new AddSound().setVisible());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}