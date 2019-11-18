package Editor.AddObjects;

import org.ini4j.Profile;
import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class AddCopy {
    private JPanel pane;
    private JFrame frame;
    private JComboBox<Object> objectCopyList;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JButton saveButton;

    AddCopy() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel objectCopyLabel = new JLabel("Choose object to copy (if no selections" +
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

        JLabel xPosLabel = new JLabel("Starting X Position");
        c.gridy = 2;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        JLabel yPosLabel = new JLabel("Starting Y Position");
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

        saveButton.addActionListener(e -> {
        	Wini ini = null;
            try {
                ini = new Wini(new File("./objects/objects.ini"));
                Set<String> keys = ini.keySet();
                int num = 0;
                while (keys.contains(Integer.toString(num))) {
                    num++;
                }

                String strNum = Integer.toString(num);
                Profile.Section copy = null;

                for(String key : keys) {
                    if(ini.get(key, "name").equals(objectCopyList.getSelectedItem())) {
                        copy = (Profile.Section) ini.get(key);
                        break;
                    }
                }

                if(copy != null) {
                    for(String name : (Set<String>)copy.keySet()) {
                        if(!name.equals("x") && !name.equals("y")) {
                            ini.put(strNum, name, copy.get(name));
                        }
                    }

                    ini.put(strNum, "x", xPosition.getValue());
                    ini.put(strNum, "y", yPosition.getValue());
                }

                ini.store();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // close frame
            frame.dispose();
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}