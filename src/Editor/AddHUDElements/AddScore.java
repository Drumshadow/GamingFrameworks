package Editor.AddHUDElements;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddScore {
    private JPanel pane;
    private JFrame frame;
    private JTextField nameTextField;
    private JSpinner startSoreSpinner;
    private JSpinner maxScoreSpinner;
    private JButton saveButton;

    AddScore() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel nameLabel = new JLabel("Name of the score display");
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

        JLabel startScoreLabel = new JLabel("Set the starting score for the game");
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        pane.add(startScoreLabel, c);

        JLabel maxScoreLabel = new JLabel("Set the maximum score for the game");
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

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Wini ini = new Wini(new File("./HUD/HUD.ini"));

                    int i = 0;
                    while(ini.containsKey(Integer.toString(i))) {
                        i++;
                    }

                    String strNum = Integer.toString(i);

                    ini.put(strNum, "type", "Score");
                    ini.put(strNum, "score", startSoreSpinner.getValue());
                    ini.put(strNum, "maxScore", maxScoreSpinner.getValue());

                    ini.put(strNum, "xPos", 50);
                    ini.put(strNum, "yPos", 80);

                    ini.put(strNum, "name", nameTextField.getText());

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
