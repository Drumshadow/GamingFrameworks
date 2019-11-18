package Editor.AddObjects;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddNew {
    private JPanel pane;
    private JFrame frame;
    private JTextField nameTextField;
    private JCheckBox collisionCheck;
    private JSpinner terminalVelocity;
    private JSpinner jumpHeight;
    private JSpinner weightSpinner;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JComboBox<String> boundingBox;
    private JButton chooseSprite;
    private JCheckBox animatedCheck;
    private JComboBox<Integer> frames;
    private JButton saveButton;

    private File sprite;

    AddNew() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel nameLabel = new JLabel("Name for the AI (Can be unique or duplicate " +
                "to allow a single event to apply to multiple objects)");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        collisionCheck = new JCheckBox("Does the object collide with other " +
                "objects?");
        c.gridy = 2;
        c.gridx = 0;

        pane.add(collisionCheck, c);

        JLabel jumpLabel = new JLabel("Jump Height");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(jumpLabel, c);

        JLabel terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 1;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        jumpHeight = new JSpinner(jumpModel);
        c.gridx = 0;
        c.gridy = 4;
        pane.add(jumpHeight, c);

        terminalVelocity = new JSpinner(tvModel);
        c.gridx = 1;
        pane.add(terminalVelocity, c);

        JLabel xPosLabel = new JLabel("Starting X Position");
        c.gridy = 5;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        JLabel yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 2000, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 2000, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 6;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        JLabel weightLabel = new JLabel("Weight of the Object");
        c.gridwidth = 2;
        c.gridy = 7;
        c.gridx = 0;
        pane.add(weightLabel, c);

        SpinnerNumberModel weightModel = new SpinnerNumberModel(0, 0, 10, 1);

        weightSpinner = new JSpinner(weightModel);
        c.gridy = 8;
        pane.add(weightSpinner, c);

        JLabel boundingBoxLabel = new JLabel("Bounding Box Type (For collision)");
        c.gridy = 9;
        pane.add(boundingBoxLabel, c);

        boundingBox = new JComboBox<>(new String[] {"Rectangle", "Oval"});
        c.gridy = 10;
        pane.add(boundingBox, c);

        chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = 11;
        pane.add(chooseSprite, c);

        animatedCheck = new JCheckBox("Is the sprite animated?");
        c.gridy = 12;
        pane.add(animatedCheck, c);

        JLabel framesLabel = new JLabel("Number of Frames (Number of frames for an " +
                "animated sprite)");
        c.gridy = 13;
        pane.add(framesLabel, c);

        frames = new JComboBox<>();

        for(int i = 1; i <= 60; i++) {
            frames.addItem(i);
        }

        c.gridy = 14;
        pane.add(frames, c);
        frames.setEnabled(false);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 15;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);

        chooseSprite.addActionListener(e -> {
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
            sprite = fc.getSelectedFile();
        });

        animatedCheck.addActionListener(e -> frames.setEnabled(!frames.isEnabled()));

        saveButton.addActionListener(e -> {
            Wini ini = null;
            try {
                ini = new Wini(new File("./objects/objects.ini"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Set<String> keys = ini.keySet();
            int num = 0;
            while(keys.contains(Integer.toString(num))) {
                num++;
            }

            String strNum = Integer.toString(num);

            try {
                ini.put(strNum, "name", nameTextField.getText());
                if(Integer.parseInt(frames.getSelectedItem().toString()) > 1) {
                    ini.put(strNum, "sprPath",
                            sprite.getAbsolutePath().substring(0,
                                    sprite.getAbsolutePath().length() - 6).replace('\\', '/'));
                } else {
                    ini.put(strNum, "sprPath",
                            sprite.getAbsolutePath().substring(0,
                                    sprite.getAbsolutePath().length() - 4).replace('\\', '/'));
                }
                ini.put(strNum, "frames", frames.getSelectedItem());
                ini.put(strNum, "collide", collisionCheck.isSelected());
                ini.put(strNum, "weight", weightSpinner.getValue());
                ini.put(strNum, "tv", terminalVelocity.getValue());
                ini.put(strNum, "jump", jumpHeight.getValue());
                ini.put(strNum, "boxType", Integer.toString(
                        boundingBox.getSelectedIndex()));
                ini.put(strNum, "x", xPosition.getValue());
                ini.put(strNum, "y", yPosition.getValue());

                ini.put(strNum, "AI", "null");
                ini.store();
            } catch(IOException err) {
                err.printStackTrace();
            }

            // close frame
            frame.dispose();
        });
    }

    public void setVisible() {
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}