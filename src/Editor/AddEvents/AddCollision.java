package Editor.AddEvents;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddCollision {
    private JPanel pane;
    private JFrame frame;
    private JLabel HUDLabel;
    private JComboBox<String> HUDComboBox;
    private JLabel object1Label;
    private JLabel object2Label;
    private JComboBox<String> object1ComboBox;
    private JComboBox<String> object2ComboBox;
    private JLabel modLabel;
    private JSpinner modSpinner;
    private JCheckBox audioCheck;
    private JButton audioSelector;
    private JButton saveButton;

    private File audioFile;

    public AddCollision() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        try {
            Wini ini = new Wini(new File("./HUD/HUD.ini"));

            Set<String> keys = ini.keySet();
            String[] values = new String[keys.size() + 1];

            keys = ini.keySet();
            values = new String[keys.size()];

            int i = 0;
            for (String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            HUDLabel = new JLabel("HUD Element that will be affected (Like " +
                    "the health)");
            c.gridy = 2;
            pane.add(HUDLabel, c);

            HUDComboBox = new JComboBox<>(values);
            c.gridy = 3;
            pane.add(HUDComboBox, c);

            c.gridwidth = 1;

            object1Label = new JLabel("Choose first object");
            c.gridy = 4;
            pane.add(object1Label, c);

            object2Label = new JLabel("Choose second object");
            c.gridx = 1;
            pane.add(object2Label, c);

            ini = new Wini(new File("./objects/objects.ini"));
            keys = ini.keySet();
            values = new String[keys.size() + 1];

            i = 1;
            for(String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            object1ComboBox = new JComboBox<>(values);
            c.gridy = 5;
            c.gridx = 0;
            pane.add(object1ComboBox, c);

            object2ComboBox = new JComboBox<>(values);
            c.gridx = 1;
            pane.add(object2ComboBox, c);
        } catch(IOException err) {
            err.printStackTrace();
        }

        c.gridwidth = 2;

        modLabel = new JLabel("Choose the amount the given element will be " +
                "changed by (like health decreases by 5)");
        c.gridy = 6;
        c.gridx = 0;
        pane.add(modLabel, c);

        SpinnerNumberModel modModel = new SpinnerNumberModel(0, -10, 10, 1);

        modSpinner = new JSpinner(modModel);
        c.gridy = 7;
        pane.add(modSpinner, c);

        audioCheck = new JCheckBox("Does the collision have a sound?");
        c.gridy = 8;
        pane.add(audioCheck, c);

        audioSelector = new JButton("Audio File");
        c.gridy = 9;
        pane.add(audioSelector, c);
        audioSelector.setEnabled(false);

        saveButton = new JButton("Save");
        c.gridy = 10;
        c.gridx = 1;
        pane.add(saveButton, c);

        audioCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                audioSelector.setEnabled(audioCheck.isSelected());
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Wini ini = new Wini(new File("./events/events.ini"));

                    int i = 0;
                    while(ini.containsKey(Integer.toString(i))) {
                        i++;
                    }

                    String strNum = Integer.toString(i);

                    ini.put(strNum, "type", "collision");

                    if(object1ComboBox.getSelectedIndex() != 0) {
                        ini.put(strNum, "obj1",
                                object1ComboBox.getSelectedItem());
                    }

                    if(object2ComboBox.getSelectedIndex() != 0) {
                        ini.put(strNum, "obj2",
                                object2ComboBox.getSelectedItem());
                    }

                    if(HUDComboBox.getSelectedIndex() != 0) {
                        ini.put(strNum, "hud", HUDComboBox.getSelectedItem());
                        ini.put(strNum, "mod", modSpinner.getValue());
                    }


                    if(audioFile != null) {
                        ini.put(strNum, "audio",
                                audioFile.getAbsolutePath().replace('\\', '/'));
                    } else {
                        ini.put(strNum, "audio", "null");
                    }

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    public void setVisible() {
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
