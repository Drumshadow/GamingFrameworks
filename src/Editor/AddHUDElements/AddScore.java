package Editor.AddHUDElements;

import javax.swing.*;
import java.awt.*;

public class AddScore {
    private JPanel pane;
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel startScoreLabel;
    private JLabel maxScoreLabel;
    private JSpinner startSoreSpinner;
    private JSpinner maxScoreSpinner;
    private JButton saveButton;

    public AddScore() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        nameLabel = new JLabel("Name of the score display");
        c.gridx = 0;
        c.gridy = 0;
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        SpinnerNumberModel startScoreModel = new SpinnerNumberModel(0, 0, 10000,
                1);
        SpinnerNumberModel maxScoreModel = new SpinnerNumberModel(0, 0, 10000,
                1);

        startScoreLabel = new JLabel("Set the starting score for the game");
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        pane.add(startScoreLabel, c);

        maxScoreLabel = new JLabel("Set the maximum score for the game");
        c.gridx = 1;
        pane.add(maxScoreLabel, c);

        startSoreSpinner = new JSpinner(startScoreModel);
        c.gridy = 4;
        pane.add(startSoreSpinner, c);

        maxScoreSpinner = new JSpinner(maxScoreModel);
        c.gridx = 1;
        pane.add(maxScoreSpinner, c);

        saveButton = new JButton("Save");
        c.gridy = 5;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
