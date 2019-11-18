package Editor.AddObjects;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JLabel boundingBoxLabel;
    private JComboBox<String> boundingBox;
    private JButton chooseSprite;
    private JCheckBox animatedCheck;
    private JLabel framesLabel;
    private JComboBox<Integer> frames;
    private JButton saveButton;

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

        SpinnerNumberModel xSpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);
        SpinnerNumberModel ySpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);

        xSpeedLabel = new JLabel("Auto X Speed Movement");
        c.gridwidth = 1;
        c.gridy = 7;
        pane.add(xSpeedLabel, c);

        ySpeedLabel = new JLabel("Auto Y Speed Movement");
        c.gridx = 1;
        pane.add(ySpeedLabel, c);

        autoXSpeed = new JSpinner(xSpeedModel);
        c.gridy = 8;
        c.gridx = 0;
        pane.add(autoXSpeed, c);
        autoXSpeed.setEnabled(false);

        autoYSpeed = new JSpinner(ySpeedModel);
        c.gridx = 1;
        pane.add(autoYSpeed, c);
        autoYSpeed.setEnabled(false);

        jumpLabel = new JLabel("Jump Height");
        c.gridx = 0;
        c.gridy = 9;
        pane.add(jumpLabel, c);

        terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 1;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        jumpHeight = new JSpinner(jumpModel);
        c.gridx = 0;
        c.gridy = 10;
        pane.add(jumpHeight, c);

        terminalVelocity = new JSpinner(tvModel);
        c.gridx = 1;
        pane.add(terminalVelocity, c);

        xPosLabel = new JLabel("Starting X Position");
        c.gridy = 11;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 12;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        boundingBoxLabel = new JLabel("Bounding Box Type (For collision)");
        c.gridy = 13;
        c.gridx = 0;
        c.gridwidth = 2;
        pane.add(boundingBoxLabel, c);

        boundingBox = new JComboBox<>(new String[] {"Rectangle", "Oval"});
        c.gridy = 14;
        pane.add(boundingBox, c);

        chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = 15;
        pane.add(chooseSprite, c);

        animatedCheck = new JCheckBox("Is the sprite animated?");
        c.gridy = 16;
        pane.add(animatedCheck, c);

        framesLabel = new JLabel("Number of Frames (Number of frames for an " +
                "animated sprite)");
        c.gridy = 17;
        pane.add(framesLabel, c);

        frames = new JComboBox<>();

        for(int i = 1; i <= 60; i++) {
            frames.addItem(i);
        }

        c.gridy = 18;
        pane.add(frames, c);
        frames.setEnabled(false);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 19;
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
    }

    public void setVisible() {
        frame.setSize(600, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
