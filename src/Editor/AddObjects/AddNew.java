package Editor.AddObjects;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddNew {
    private JPanel pane;
    private JFrame frame;
    private JCheckBox collisionCheck;
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

    public AddNew() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;

        pane = new JPanel(grid);
        frame = new JFrame();

        collisionCheck = new JCheckBox("Does the object collide with other " +
                "objects?");
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);
        pane.add(collisionCheck, c);

        jumpLabel = new JLabel("Jump Height");
        c.gridx = 0;
        c.gridy = 7;
        pane.add(jumpLabel, c);

        terminalVelocityLabel = new JLabel("Terminal Velocity");
        c.gridx = 1;
        pane.add(terminalVelocityLabel, c);

        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 10, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 10, 1);

        jumpHeight = new JSpinner(jumpModel);
        c.gridx = 0;
        c.gridy = 8;
        pane.add(jumpHeight, c);

        terminalVelocity = new JSpinner(tvModel);
        c.gridx = 1;
        pane.add(terminalVelocity, c);

        xPosLabel = new JLabel("Starting X Position");
        c.gridy = 9;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 10;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        boundingBoxLabel = new JLabel("Bounding Box Type (For collision)");
        c.gridy = 11;
        c.gridx = 0;
        c.gridwidth = 2;
        pane.add(boundingBoxLabel, c);

        boundingBox = new JComboBox<>(new String[] {"Rectangle", "Oval"});
        c.gridy = 12;
        pane.add(boundingBox, c);

        chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = 13;
        pane.add(chooseSprite, c);

        animatedCheck = new JCheckBox("Is the sprite animated?");
        c.gridy = 14;
        pane.add(animatedCheck, c);

        framesLabel = new JLabel("Number of Frames (Number of frames for an " +
                "animated sprite)");
        c.gridy = 15;
        pane.add(framesLabel, c);

        frames = new JComboBox<>();

        for(int i = 1; i <= 60; i++) {
            frames.addItem(i);
        }

        c.gridy = 16;
        pane.add(frames, c);
        frames.setEnabled(false);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 17;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);

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
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
