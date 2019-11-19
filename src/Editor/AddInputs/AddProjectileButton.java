package Editor.AddInputs;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class AddProjectileButton {
    private JFrame frame;
    private JPanel pane;

    private JCheckBox collisionCheck;
    private JCheckBox bounceHorizontalCheck;
    private JCheckBox bounceVerticalCheck;
    private JCheckBox audioCheck;

    private JButton audioSelector;

    private JCheckBox destructsCheckBox;
    private JList<String> destructorsList;

    private JComboBox<String> xSpeedCombo;
    private JComboBox<String> ySpeedCombo;
    private JComboBox<String> firingObjCombo;
    private JComboBox<String> boundingBox;
    private JComboBox<Integer> frames;
    private JComboBox<String> mapToComboBox;

    private JSpinner terminalVelocity;
    private JSpinner weightSpinner;

    private File audioFile;
    private File sprite;

    AddProjectileButton() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;

        int gY = 0;

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel keyMapLabel = new JLabel("Choose Key to Map Firing To");
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(keyMapLabel, c);

        mapToComboBox = new JComboBox<>();

        for(int i = 49; i <= 90; i++) {
            if(i != 57 && i != 58 && i != 60 && i != 80 && !(i < 65 && i > 61)) {
                mapToComboBox.addItem((char) i + "");
            }
        }
        c.gridy = gY++;
        pane.add(mapToComboBox, c);

        JLabel objectLabel = new JLabel("Choose firing object");
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(objectLabel, c);

        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));
            Set<String> keys = ini.keySet();
            String[] values = new String[keys.size() + 1];

            int i = 1;
            for (String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            firingObjCombo = new JComboBox<>(values);
            c.gridy = gY++;
            pane.add(firingObjCombo, c);

            collisionCheck = new JCheckBox("Can the projectile hit other objects?");
            c.gridy = gY++;
            pane.add(collisionCheck, c);

            bounceHorizontalCheck = new JCheckBox("Can the projectile bounce off of objects horizontally?");
            c.gridy = gY++;
            pane.add(bounceHorizontalCheck, c);

            bounceVerticalCheck = new JCheckBox("Can the projectile bounce off of objects vertically?");
            c.gridy = gY++;
            pane.add(bounceVerticalCheck, c);

            destructsCheckBox = new JCheckBox("Is the projectile destroyable by another " +
                    "object?");
            c.gridy = gY++;
            pane.add(destructsCheckBox, c);

            i = 1;
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

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        JLabel xSpeedLabel = new JLabel("Projectile X Speed");
        c.gridwidth = 1;
        c.gridy = gY++;
        pane.add(xSpeedLabel, c);

        JLabel ySpeedLabel = new JLabel("Projectile Y Speed");
        c.gridx = 1;
        pane.add(ySpeedLabel, c);

        xSpeedCombo = new JComboBox<>(new String[] {"None", "Slow Left", "Medium Left", "Fast Left",
                "Slow Right", "Medium Right", "Fast Right"});
        c.gridy = gY++;
        c.gridx = 0;
        pane.add(xSpeedCombo, c);

        ySpeedCombo = new JComboBox<>(new String[] {"None", "Slow Down", "Medium Down", "Fast Down",
                "Slow Up", "Medium Up", "Fast Up"});
        c.gridx = 1;
        pane.add(ySpeedCombo, c);

        JLabel terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 0;
        c.gridy = gY++;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        terminalVelocity = new JSpinner(tvModel);
        c.gridwidth = 2;
        c.gridy = gY++;
        pane.add(terminalVelocity, c);

        JLabel weightLabel = new JLabel("Projectile Weight");
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

        audioCheck = new JCheckBox("Does the projectile have a sound?");
        c.gridy = gY++;
        pane.add(audioCheck, c);

        audioSelector = new JButton("Audio File");
        c.gridy = gY++;
        pane.add(audioSelector, c);
        audioSelector.setEnabled(false);

        JButton saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = gY;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new JScrollPane(pane));
        frame.pack();

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
        audioCheck.addActionListener(e -> audioSelector.setEnabled(audioCheck.isSelected()));

        audioSelector.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getPath().matches(".*\\.ogg$");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fc.showOpenDialog(pane);
            audioFile = fc.getSelectedFile();
        });

        saveButton.addActionListener(e -> {
            try {
                Wini ini = new Wini(new File("./inputs/keyboard.ini"));

                int num = 0;
                while (ini.containsKey(Integer.toString(num))) {
                    num++;
                }
                String strNum = Integer.toString(num);

                // key and action
                ini.put(strNum, "key",
                        Integer.toString((int) ((String) Objects.requireNonNull(mapToComboBox.getSelectedItem())).charAt(0)));
                ini.put(strNum, "action", "1");

                // firing object
                ini.put(strNum, "object", firingObjCombo.getSelectedItem());

                // projectile name
                ini.put(strNum, "o2name", "iProjectile" + strNum);

                // projectile sprite
                if(Integer.parseInt(Objects.requireNonNull(frames.getSelectedItem()).toString()) > 1) {
                    ini.put(strNum, "o2path",
                            sprite.getAbsolutePath().substring(0,
                                    sprite.getAbsolutePath().length() - 6).replace('\\', '/'));
                } else {
                    ini.put(strNum, "o2path",
                            sprite.getAbsolutePath().substring(0,
                                    sprite.getAbsolutePath().length() - 4).replace('\\', '/'));
                }

                // other projectile attributes
                ini.put(strNum, "o2frames", frames.getSelectedItem());
                ini.put(strNum, "o2collide", collisionCheck.isSelected());
                ini.put(strNum, "o2weight", weightSpinner.getValue());
                ini.put(strNum, "o2tv", terminalVelocity.getValue());
                ini.put(strNum, "o2jump", "0.0");
                ini.put(strNum, "o2boxType", Integer.toString(
                        boundingBox.getSelectedIndex()));

                double xS = 0.0;
                double yS = 0.0;

                // projectile x speed
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

                // projectile y speed
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

                // AI
                String AI = "";

                if(bounceHorizontalCheck.isSelected()) {
                    AI += "walls";
                }
                if(bounceVerticalCheck.isSelected()) {
                    if(AI.length() != 0) {
                        AI += ",";
                    }
                    AI += "bounce";
                }

                // destroyers
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

                // audio
                if(audioFile != null) {
                    ini.put(strNum, "audio",
                            audioFile.getAbsolutePath().replace('\\', '/'));
                } else {
                    ini.put(strNum, "audio", "null");
                }

                // purpose
                ini.put(strNum, "purpose", "Fire");

                ini.store();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            // close frame
            frame.dispose();
        });
    }

    public void setVisible() {
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}