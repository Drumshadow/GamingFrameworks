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
    private JCheckBox collisionCheck;
    private JCheckBox ledgeCheck;
    private JCheckBox bounceHorizontalCheck;
    private JCheckBox bounceVerticalCheck;
    private JCheckBox autoMoveCheck;
    private JLabel xSpeedLabel;
    private JLabel ySpeedLabel;
    private JSpinner autoXSpeed;
    private JSpinner autoYSpeed;
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JButton chooseSprite;

    private File sprite;

    public AddAI() {
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

        ledgeCheck = new JCheckBox("Does the object fear ledges? (Rebounds " +
                "away from them)");
        c.gridy = 1;
        pane.add(ledgeCheck, c);

        bounceHorizontalCheck = new JCheckBox("Does the object bounce off " +
                "walls and other objects horizontally? (Reverse X direction)");
        c.gridy = 2;
        pane.add(bounceHorizontalCheck, c);

        bounceVerticalCheck = new JCheckBox("Does the object bounce of floors" +
                " and other objects vertically? (Reverse Y direction)");
        c.gridy = 3;
        pane.add(bounceVerticalCheck, c);

        autoMoveCheck = new JCheckBox("Does the object move on its own?");
        c.gridy = 4;
        pane.add(autoMoveCheck, c);

        SpinnerNumberModel xSpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);
        SpinnerNumberModel ySpeedModel = new SpinnerNumberModel(0, -10, 10,
                0.5);

        xSpeedLabel = new JLabel("Auto X Speed Movement");
        c.gridwidth = 1;
        c.gridy = 5;
        pane.add(xSpeedLabel, c);

        ySpeedLabel = new JLabel("Auto Y Speed Movement");
        c.gridx = 1;
        pane.add(ySpeedLabel, c);

        autoXSpeed = new JSpinner(xSpeedModel);
        c.gridy = 6;
        c.gridx = 0;
        pane.add(autoXSpeed, c);
        autoXSpeed.setEnabled(false);

        autoYSpeed = new JSpinner(ySpeedModel);
        c.gridx = 1;
        pane.add(autoYSpeed, c);
        autoYSpeed.setEnabled(false);


        xPosLabel = new JLabel("Starting X Position");
        c.gridy = 7;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 8;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        chooseSprite = new JButton("Sprite File (Requires PNG)");
        c.gridy = 9;
        c.gridx = 0;
        c.gridwidth = 2;
        pane.add(chooseSprite, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
        frame.pack();

        autoMoveCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autoMoveCheck.isSelected()) {
                    autoXSpeed.setEnabled(true);
                    autoYSpeed.setEnabled(true);
                } else {
                    autoXSpeed.setEnabled(false);
                    autoYSpeed.setEnabled(false);
                }
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
    }

    public void setVisible() {
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
