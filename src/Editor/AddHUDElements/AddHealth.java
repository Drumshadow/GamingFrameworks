package Editor.AddHUDElements;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddHealth {
    private JPanel pane;
    private JFrame frame;
    private JTextField nameTextField;
    private JComboBox<String> healthBarTypeComboBox;
    private JSpinner livesSpinner;
    private JSpinner maxLivesSpinner;
    private JButton spriteFile;
    private JButton saveButton;

    private File sprPath;

    AddHealth() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel nameLabel = new JLabel("Name of the health display");
        c.gridx = 0;
        c.gridy = 0;
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        JLabel healthBarTypeLabel = new JLabel("Select the type of health bar you " +
                "want");
        c.gridy = 2;
        pane.add(healthBarTypeLabel, c);

        healthBarTypeComboBox = new JComboBox<>(new String[] {"SPRITE", "BAR",
                "NUM"});
        c.gridy = 3;
        pane.add(healthBarTypeComboBox, c);

        spriteFile = new JButton("Sprite File");
        c.gridy = 4;
        pane.add(spriteFile, c);

        SpinnerNumberModel livesModel = new SpinnerNumberModel(5, 0, 10, 1);
        SpinnerNumberModel maxLivesModel = new SpinnerNumberModel(0, 0, 10,
                1);

        JLabel livesLabel = new JLabel("Select the starting number of lives/health");
        c.gridwidth = 1;
        c.gridy = 5;
        pane.add(livesLabel, c);

        JLabel maxLivesLabel = new JLabel("Select the maximum number of lives/health");
        c.gridx = 1;
        pane.add(maxLivesLabel, c);

        livesSpinner = new JSpinner(livesModel);
        c.gridy = 6;
        c.gridx = 0;
        pane.add(livesSpinner, c);

        maxLivesSpinner = new JSpinner(maxLivesModel);
        c.gridx = 1;
        pane.add(maxLivesSpinner, c);

        saveButton = new JButton("Save");
        c.gridy = 7;
        pane.add(saveButton, c);

        healthBarTypeComboBox.addActionListener(e -> {
            if(healthBarTypeComboBox.getSelectedIndex() != 0) {
                spriteFile.setEnabled(false);
            } else {
                spriteFile.setEnabled(true);
            }
        });

        spriteFile.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getPath().matches(".*\\.png$");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fc.showOpenDialog(pane);
            sprPath = fc.getSelectedFile();
        });

        saveButton.addActionListener(e -> {
            try {
                Wini ini = new Wini(new File("./HUD/HUD.ini"));

                int i = 0;
                while(ini.containsKey(Integer.toString(i))) {
                    i++;
                }

                String strNum = Integer.toString(i);
                ini.put(strNum, "type", "HealthBar");
                ini.put(strNum, "hType", healthBarTypeComboBox.getSelectedItem());
                ini.put(strNum, "max", maxLivesSpinner.getValue());
                ini.put(strNum, "lives", livesSpinner.getValue());
                ini.put(strNum, "width", "0.5");
                ini.put(strNum, "height", "0.05");

                if(sprPath != null) {
                    ini.put(strNum, "spPath",
                            sprPath.getAbsolutePath().substring(0,
                                    sprPath.getAbsolutePath().length() - 4).replace('\\',
                                    '/'));
                    ini.put(strNum, "spFrames", "1");
                }

                ini.put(strNum, "xPos", "-0.9");
                ini.put(strNum, "yPos", "0.85");

                ini.put(strNum, "name", nameTextField.getText());

                ini.store();
            } catch(IOException err) {
                err.printStackTrace();
            }

            // close frame
            frame.dispose();
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
