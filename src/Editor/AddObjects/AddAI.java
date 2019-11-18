package Editor.AddObjects;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddAI {
    private JPanel pane;
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JCheckBox collisionCheck;
    private JCheckBox ledgeCheck;
    private JCheckBox bounceHorizontalCheck;
    private JCheckBox bounceVerticalCheck;
    private JCheckBox autoMoveCheck;
    private JLabel xSpeedLabel;
    private JLabel ySpeedLabel;
    private JSpinner autoXSpeed;
    private JSpinner autoYSpeed;
    private JLabel terminalVelocityLabel;
    private JSpinner terminalVelocity;
    private JLabel jumpLabel;
    private JSpinner jumpHeight;
    private JLabel weightLabel;
    private JSpinner weightSpinner;
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JLabel boundingBoxLabel;
    private JComboBox<String> boundingBox;
    private JButton chooseSprite;
    private JCheckBox animatedCheck;
    private JCheckBox destructsCheckBox;
    private JLabel framesLabel;
    private JComboBox<Integer> frames;
    private JButton saveButton;
    private JList<String> destructorsList;

    private File sprite;

    public AddAI() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;

        pane = new JPanel(grid);
        frame = new JFrame();

        nameLabel = new JLabel("Name for the AI (Can be unique or duplicate " +
                "to allow a single event to apply to multiple AI)");
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        collisionCheck = new JCheckBox("Does the object collide with other " +
                "objects?");
        c.gridy = 2;
        pane.add(collisionCheck, c);

        ledgeCheck = new JCheckBox("Does the object fear ledges? (Rebounds " +
                "away from them)");
        c.gridy = 3;
        pane.add(ledgeCheck, c);

        bounceHorizontalCheck = new JCheckBox("Does the object bounce off " +
                "walls and other objects horizontally? (Reverse X direction)");
        c.gridy = 4;
        pane.add(bounceHorizontalCheck, c);

        bounceVerticalCheck = new JCheckBox("Does the object bounce of floors" +
                " and other objects vertically? (Reverse Y direction)");
        c.gridy = 5;
        pane.add(bounceVerticalCheck, c);

        autoMoveCheck = new JCheckBox("Does the object move on its own?");
        c.gridy = 6;
        pane.add(autoMoveCheck, c);

        destructsCheckBox = new JCheckBox("Is the AI Destroyable by Another " +
                "Object?");
        c.gridy = 7;
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
            c.gridy = 8;
            pane.add(destructorsList, c);
            destructorsList.setEnabled(false);

            destructsCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    destructorsList.setEnabled(destructsCheckBox.isSelected());
                }
            });
        } catch(IOException err) {
            err.printStackTrace();
        }

        SpinnerNumberModel xSpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);
        SpinnerNumberModel ySpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);

        xSpeedLabel = new JLabel("Auto X Speed Movement");
        c.gridwidth = 1;
        c.gridy = 9;
        pane.add(xSpeedLabel, c);

        ySpeedLabel = new JLabel("Auto Y Speed Movement");
        c.gridx = 1;
        pane.add(ySpeedLabel, c);

        autoXSpeed = new JSpinner(xSpeedModel);
        c.gridy = 10;
        c.gridx = 0;
        pane.add(autoXSpeed, c);
        autoXSpeed.setEnabled(false);

        autoYSpeed = new JSpinner(ySpeedModel);
        c.gridx = 1;
        pane.add(autoYSpeed, c);
        autoYSpeed.setEnabled(false);

        jumpLabel = new JLabel("Jump Height");
        c.gridx = 0;
        c.gridy = 11;
        pane.add(jumpLabel, c);

        terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 1;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        jumpHeight = new JSpinner(jumpModel);
        c.gridx = 0;
        c.gridy = 12;
        pane.add(jumpHeight, c);

        terminalVelocity = new JSpinner(tvModel);
        c.gridx = 1;
        pane.add(terminalVelocity, c);

        xPosLabel = new JLabel("Starting X Position");
        c.gridy = 13;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 2000, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 2000, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 14;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        weightLabel = new JLabel("Weight of the Object");
        c.gridwidth = 2;
        c.gridy = 15;
        c.gridx = 0;
        pane.add(weightLabel, c);

        SpinnerNumberModel weightModel = new SpinnerNumberModel(0, 0, 10, 1);

        weightSpinner = new JSpinner(weightModel);
        c.gridy = 16;
        pane.add(weightSpinner, c);

        boundingBoxLabel = new JLabel("Bounding Box Type (For collision)");
        c.gridy = 17;
        pane.add(boundingBoxLabel, c);

        boundingBox = new JComboBox<>(new String[] {"Rectangle", "Oval"});
        c.gridy = 18;
        pane.add(boundingBox, c);

        chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = 19;
        pane.add(chooseSprite, c);

        animatedCheck = new JCheckBox("Is the sprite animated?");
        c.gridy = 20;
        pane.add(animatedCheck, c);

        framesLabel = new JLabel("Number of Frames (Number of frames for an " +
                "animated sprite)");
        c.gridy = 21;
        pane.add(framesLabel, c);

        frames = new JComboBox<>();

        for(int i = 1; i <= 60; i++) {
            frames.addItem(i);
        }

        c.gridy = 22;
        pane.add(frames, c);
        frames.setEnabled(false);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 23;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
        frame.pack();

        autoMoveCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoXSpeed.setEnabled(!autoXSpeed.isEnabled());
                autoYSpeed.setEnabled(!autoYSpeed.isEnabled());
            }
        });

        chooseSprite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        animatedCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frames.setEnabled(!frames.isEnabled());
            }
        });

        saveButton.addActionListener(new ActionListener() {
            private Wini ini;
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        ini.put(strNum, "xSpeed", autoXSpeed.getValue());
                        ini.put(strNum, "ySpeed", autoYSpeed.getValue());
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
                    err.printStackTrace();
                }
            }
        });
    }

    public void setVisible() {
        frame.setSize(600, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
