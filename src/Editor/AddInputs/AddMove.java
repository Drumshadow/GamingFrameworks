package Editor.AddInputs;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddMove {
    private JPanel pane;
    private JFrame frame;
    private JComboBox<String> mapToComboBox;
    private JComboBox<String> objectComboBox;
    private JSpinner speedSpinner;
    private JButton saveButton;

    AddMove() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel keyMapLabel = new JLabel("Choose Key to Map Movement To");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(keyMapLabel, c);

        mapToComboBox = new JComboBox<>();

        for(int i = 48; i <= 90; i++) {
            if(i != 58 && i != 60 && !(i < 65 && i > 61)) {
                mapToComboBox.addItem((char) i + "");
            }
        }

        c.gridy = 1;
        pane.add(mapToComboBox, c);

        JLabel objectLabel = new JLabel("Select the object to apply the move to");
        c.gridy = 2;
        pane.add(objectLabel, c);

        objectComboBox = new JComboBox<>();

        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));

            Set<String> keys = ini.keySet();
            for(String key : keys) {
                System.out.println(ini.get(key, "name"));
                objectComboBox.addItem(ini.get(key, "name"));
            }
        } catch(IOException err) {
            System.out.println(err.getMessage());
        }

        c.gridy = 3;
        pane.add(objectComboBox, c);

        JLabel speedLabel = new JLabel("Set the speed of the object during the move");
        c.gridy = 4;
        pane.add(speedLabel, c);

        SpinnerNumberModel speedModel = new SpinnerNumberModel(0, -20.0, 20.0,
                0.5);

        speedSpinner = new JSpinner(speedModel);
        c.gridy = 5;
        pane.add(speedSpinner, c);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 6;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
