package Editor.AddHUDElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddHealth {
    private JPanel pane;
    private JFrame frame;
    private JLabel healthBarTypeLabel;
    private JComboBox<String> healthBarTypeComboBox;
    private JLabel livesLabel;
    private JLabel maxLivesLabel;
    private JSpinner livesSpinner;
    private JSpinner maxLivesSpinner;
    private JButton spriteFile;
    private JButton saveButton;

    private File sprPath;

    public AddHealth() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        healthBarTypeLabel = new JLabel("Select the type of health bar you " +
                "want");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(healthBarTypeLabel, c);

        healthBarTypeComboBox = new JComboBox<>(new String[] {"SPRITE", "BAR",
                "NUM"});
        c.gridy = 1;
        pane.add(healthBarTypeComboBox, c);

        spriteFile = new JButton("Sprite File");
        c.gridy = 2;
        pane.add(spriteFile, c);

        SpinnerNumberModel livesModel = new SpinnerNumberModel(5, 0, 10, 1);
        SpinnerNumberModel maxLivesModel = new SpinnerNumberModel(0, 0, 10,
                1);

        livesLabel = new JLabel("Select the starting number of lives/health");
        c.gridwidth = 1;
        c.gridy = 3;
        pane.add(livesLabel, c);

        maxLivesLabel = new JLabel("Select the maximum number of lives/health");
        c.gridx = 1;
        pane.add(maxLivesLabel, c);

        livesSpinner = new JSpinner(livesModel);
        c.gridy = 4;
        c.gridx = 0;
        pane.add(livesSpinner, c);

        maxLivesSpinner = new JSpinner(maxLivesModel);
        c.gridx = 1;
        pane.add(maxLivesSpinner, c);

        saveButton = new JButton("Save");
        c.gridy = 5;
        pane.add(saveButton, c);

        healthBarTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(healthBarTypeComboBox.getSelectedIndex() != 0) {
                    spriteFile.setEnabled(false);
                } else {
                    spriteFile.setEnabled(true);
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
