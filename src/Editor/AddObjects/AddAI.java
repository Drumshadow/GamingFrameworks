package Editor.AddObjects;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddAI {
    private JPanel pane;
    private JFrame frame;

    private JTextField nameTextField;

    private JCheckBox collisionCheck;
    private JCheckBox ledgeCheck;
    private JCheckBox bounceHorizontalCheck;
    private JCheckBox bounceVerticalCheck;
    private JCheckBox autoMoveCheck;

    private JCheckBox destructsCheckBox;
    private JList<String> destructorsList;

    private JComboBox<String> xSpeedCombo;
    private JComboBox<String> ySpeedCombo;

    private JSpinner terminalVelocity;
    private JSpinner jumpHeight;
    private JSpinner weightSpinner;
    private JSpinner xPosition;
    private JSpinner yPosition;

    private JComboBox<String> boundingBox;
    private JComboBox<Integer> frames;

    private File sprite;

    AddAI() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;

        int gY = 0;

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel nameLabel = new JLabel("Name for the AI (Can be unique or duplicate " +
                "to allow a single event to apply to multiple AI)");
        c.gridy = gY++;
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = gY++;
        pane.add(nameTextField, c);

        collisionCheck = new JCheckBox("Does the object collide with other " +
                "objects?");
        c.gridy = gY++;
        pane.add(collisionCheck, c);

        ledgeCheck = new JCheckBox("Does the object fear ledges? (Rebounds " +
                "away from them)");
        c.gridy = gY++;
        pane.add(ledgeCheck, c);

        bounceHorizontalCheck = new JCheckBox("Does the object bounce off " +
                "walls and other objects horizontally? (Reverse X direction)");
        c.gridy = gY++;
        pane.add(bounceHorizontalCheck, c);

        bounceVerticalCheck = new JCheckBox("Does the object bounce of floors" +
                " and other objects vertically? (Reverse Y direction)");
        c.gridy = gY++;
        pane.add(bounceVerticalCheck, c);

        autoMoveCheck = new JCheckBox("Does the object move on its own?");
        c.gridy = gY++;
        pane.add(autoMoveCheck, c);

        destructsCheckBox = new JCheckBox("Is the AI Destroyable by Another " +
                "Object?");
        c.gridy = gY++;
        pane.add(destructsCheckBox, c);

        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));
            Set<String> keys = ini.keySet();
            String[] values = new String[keys.size()];

            int i = 0;
            for(String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            destructorsList = new JList<>(values);
            c.gridy = gY++;
            pane.add(destructorsList, c);
            destructorsList.setEnabled(false);

            destructsCheckBox.addActionListener(e ->
                    destructorsList.setEnabled(destructsCheckBox.isSelected()));

        } catch(IOException err) {
            System.out.println(err.getMessage());
        }

        JLabel xSpeedLabel = new JLabel("Auto X Speed Movement");
        c.gridwidth = 1;
        c.gridy = gY++;
        pane.add(xSpeedLabel, c);

        JLabel ySpeedLabel = new JLabel("Auto Y Speed Movement");
        c.gridx = 1;
        pane.add(ySpeedLabel, c);

        xSpeedCombo = new JComboBox<>(new String[] {"None", "Slow Left", "Medium Left", "Fast Left",
                "Slow Right", "Medium Right", "Fast Right"});
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(xSpeedCombo, c);
        xSpeedCombo.setEnabled(false);

        ySpeedCombo = new JComboBox<>(new String[] {"None", "Slow Down", "Medium Down", "Fast Down",
                "Slow Up", "Medium Up", "Fast Up"});
        c.gridx = 1;
        pane.add(ySpeedCombo, c);
        ySpeedCombo.setEnabled(false);

        JLabel jumpLabel = new JLabel("Jump Height");
        c.gridx = 0;
        c.gridy = gY++;
        pane.add(jumpLabel, c);

        JLabel terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 1;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        jumpHeight = new JSpinner(jumpModel);
        c.gridx = 0;
        c.gridy = gY++;
        pane.add(jumpHeight, c);

        terminalVelocity = new JSpinner(tvModel);
        c.gridx = 1;
        pane.add(terminalVelocity, c);

        JLabel xPosLabel = new JLabel("Starting X Position");
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        JLabel yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 2000, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 2000, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        JLabel weightLabel = new JLabel("Weight of the Object");
        c.gridwidth = 2;
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(weightLabel, c);

        SpinnerNumberModel weightModel = new SpinnerNumberModel(0, 0, 10, 1);

        weightSpinner = new JSpinner(weightModel);
        c.gridy = gY++;
        pane.add(weightSpinner, c);

        JLabel boundingBoxLabel = new JLabel("Bounding Box Type (For collision)");
        c.gridy = gY++;
        pane.add(boundingBoxLabel, c);

        boundingBox = new JComboBox<>(new String[] {"Rectangle", "Oval"});
        c.gridy = gY++;
        pane.add(boundingBox, c);

        JButton chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = gY++;
        pane.add(chooseSprite, c);

        JCheckBox animatedCheck = new JCheckBox("Is the sprite animated?");
        c.gridy = gY++;
        pane.add(animatedCheck, c);

        JLabel framesLabel = new JLabel("Number of Frames (Number of frames for an " +
                "animated sprite)");
        c.gridy = gY++;
        pane.add(framesLabel, c);

        frames = new JComboBox<>();

        for(int i = 1; i <= 60; i++) {
            frames.addItem(i);
        }

        c.gridy = gY++;
        pane.add(frames, c);
        frames.setEnabled(false);

        JButton saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = gY;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new JScrollPane(pane));
        frame.pack();

        autoMoveCheck.addActionListener(e -> {
            xSpeedCombo.setEnabled(!xSpeedCombo.isEnabled());
            ySpeedCombo.setEnabled(!ySpeedCombo.isEnabled());
        });

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
                System.out.println(ex.getMessage());
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

                String AI = "";

                if(ledgeCheck.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "ledges";
                }
                if(bounceHorizontalCheck.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "walls";
                }
                if(bounceVerticalCheck.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "bounce";
                }
                if(autoMoveCheck.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "auto";

                    // get auto speeds
                    double xS = 0.0;
                    double yS = 0.0;

                    // x speed
                    if (xSpeedCombo.getSelectedItem() != null) {

                        if (xSpeedCombo.getSelectedItem().equals("None")) {
                            xS = 0.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Slow Left")) {
                            xS = -4.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Medium Left")) {
                            xS = -8.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Fast Left")) {
                            xS = -12.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Slow Right")) {
                            xS = 4.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Medium Right")) {
                            xS = 8.0;
                        }
                        else if (xSpeedCombo.getSelectedItem().equals("Fast Right")) {
                            xS = 12.0;
                        }
                    }
                    ini.put(strNum, "xSpeed", xS);

                    // y speed
                    if (ySpeedCombo.getSelectedItem() != null) {

                        if (ySpeedCombo.getSelectedItem().equals("None")) {
                            yS = 0.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Slow Down")) {
                            yS = -4.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Medium Down")) {
                            yS = -8.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Fast Down")) {
                            yS = -12.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Slow Up")) {
                            yS = 4.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Medium Up")) {
                            yS = 8.0;
                        }
                        else if (ySpeedCombo.getSelectedItem().equals("Fast Up")) {
                            yS = 12.0;
                        }
                    }
                    ini.put(strNum, "ySpeed", yS);
                }

                if(destructsCheckBox.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "destruct";

                    StringBuilder destroyers = new StringBuilder();
                    for(String value: destructorsList.getSelectedValuesList()) {
                        if(destroyers.length() != 0) {
                            destroyers.append(",");
                        }

                        destroyers.append(value);
                    }

                    ini.put(strNum, "destroyers", destroyers);
                }

                if(AI.length() != 0) {
                    ini.put(strNum, "AI", AI);
                } else {
                    ini.put(strNum, "AI", "null");
                }

                ini.store();
            } catch(IOException err) {
                System.out.println(err.getMessage());
            }

            // close frame
            frame.dispose();
        });
    }

    public void setVisible() {
        frame.setSize(600, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
