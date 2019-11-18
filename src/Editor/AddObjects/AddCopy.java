package Editor.AddObjects;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class AddCopy {
    private JPanel pane;
    private JFrame frame;
    private JLabel objectCopyLabel;
    private JComboBox<Object> objectCopyList;
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JButton saveButton;

    public AddCopy() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;

        pane = new JPanel(grid);
        frame = new JFrame();

        objectCopyLabel = new JLabel("Choose object to copy (if no selections" +
                " appear, then no objects have been created)");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 20, 0);
        pane.add(objectCopyLabel, c);

        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));

            Set<String> keys = ini.keySet();
            Set<String> names = new TreeSet<>();
            for(String key : keys) {
                names.add(ini.get(key, "name"));
            }

            objectCopyList = new JComboBox<>(names.toArray());
            c.gridy = 1;
            pane.add(objectCopyList, c);
        } catch(IOException err) {
            err.printStackTrace();
        }

        c.gridwidth = 1;

        xPosLabel = new JLabel("Starting X Position");
        c.gridy = 2;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Starting Y Position");
        c.gridx = 1;
        pane.add(yPosLabel, c);

        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);

        xPosition = new JSpinner(xPosModel);
        c.gridy = 3;
        c.gridx = 0;
        pane.add(xPosition, c);

        yPosition = new JSpinner(yPosModel);
        c.gridx = 1;
        pane.add(yPosition, c);

        saveButton = new JButton("Save");
        c.gridy = 4;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
