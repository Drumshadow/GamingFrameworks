package Editor.AddEvents;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddGameEnd {
    private JPanel pane;
    private JFrame frame;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel HUDLabel;
    private JComboBox<String> HUDComboBox;
    private JLabel msgLabel;
    private JTextField msgTextField;
    private JLabel positionLabel;
    private JLabel xPosLabel;
    private JLabel yPosLabel;
    private JSpinner xPosition;
    private JSpinner yPosition;
    private JButton saveButton;

    public AddGameEnd() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        nameLabel = new JLabel("Name for the AI (Can be unique or duplicate " +
                "to allow a single event to apply to multiple objects)");
        c.gridy = 0;
        c.gridx = 0;

        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        try {
            Wini ini = new Wini(new File("./HUD/HUD.ini"));

            Set<String> keys;
            String[] values;

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
        } catch(IOException err) {
            err.printStackTrace();
        }

        msgLabel = new JLabel("End Game Message");
        c.gridy = 4;
        pane.add(nameLabel, c);

        msgTextField = new JTextField();
        c.gridy = 5;
        pane.add(nameTextField, c);

        positionLabel = new JLabel("Position to display the final text at");
        c.gridy = 8;
        c.gridx = 0;
        c.gridwidth = 2;
        pane.add(positionLabel, c);

        c.gridwidth = 1;

        xPosLabel = new JLabel("X Position");
        c.gridy = 9;
        c.gridx = 0;
        pane.add(xPosLabel, c);

        yPosLabel = new JLabel("Y Position");
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

        saveButton = new JButton("Save");
        c.gridy = 11;
        pane.add(saveButton, c);

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

                    ini.put(strNum, "type", "end");
                    ini.put(strNum, "hud", HUDComboBox.getSelectedIndex());
                    ini.put(strNum, "msg", msgTextField);
                    ini.put(strNum, "x", xPosition.getValue());
                    ini.put(strNum, "y", yPosition.getValue());
                    ini.put(strNum, "audio", "null");

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
