package Editor.AddObjects;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddCopy {
    private JPanel pane;
    private JFrame frame;
    private JLabel objectCopyLabel;
    private JComboBox<String> objectCopyList;
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;

    public AddCopy() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;

        pane = new JPanel(grid);
        frame = new JFrame();

        objectCopyLabel = new JLabel("Choose object to copy");
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 20, 0);
        pane.add(objectCopyLabel, c);

        objectCopyList = new JComboBox<>();

        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));

            Set<String> keys = ini.keySet();
            for(String key : keys) {
                System.out.println(ini.get(key, "name"));
                objectCopyList.addItem(ini.get(key, "name"));
            }
        } catch(IOException err) {
            err.printStackTrace();
        }

        c.gridy = 1;
        pane.add(objectCopyList, c);

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

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
