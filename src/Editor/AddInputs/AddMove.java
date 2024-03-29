package Editor.AddInputs;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class AddMove {
    private JFrame frame;
    private JComboBox<String> mapToComboBox;
    private JComboBox<String> objectComboBox;
    private JComboBox<String> speedCombo;
    private JComboBox<String> directionComboBox;

    AddMove() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        JPanel pane = new JPanel(grid);
        frame = new JFrame();

        JLabel keyMapLabel = new JLabel("Choose Key to Map Movement To");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(keyMapLabel, c);

        mapToComboBox = new JComboBox<>();

        for(int i = 49; i <= 90; i++) {
            if(i != 57 && i != 58 && i != 60 && i != 80 && !(i < 65 && i > 61)) {
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

        speedCombo = new JComboBox<>(new String[] {"Positive Fast", "Positive" +
                " Medium", "Positive Slow", "Negative Fast", "Negative " +
                "Medium", "Negative Slow"});
        c.gridy = 5;
        pane.add(speedCombo, c);

        JLabel directionLabel = new JLabel("Choose the direction the object will " +
                "move in");
        c.gridy = 6;
        pane.add(directionLabel, c);

        directionComboBox = new JComboBox<>(new String[] {"MoveX", "MoveY"});
        c.gridy = 7;
        pane.add(directionComboBox, c);

        JButton saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 8;
        c.gridx = 1;
        pane.add(saveButton, c);

        saveButton.addActionListener(e -> {
            try {
                Wini ini = new Wini(new File("./inputs/keyboard.ini"));
                int num = 0;

                while(ini.containsKey(Integer.toString(num)) ||
                        ini.containsKey(Integer.toString(num + 1)) ||
                        ini.containsKey(Integer.toString(num + 2))) {
                    num++;
                }

                int speed;

                if(speedCombo.getSelectedItem().equals("Positive Max")) {
                    speed = 8;
                } else if(speedCombo.getSelectedItem().equals("Positive " +
                        "Medium")) {
                    speed = 5;
                } else if(speedCombo.getSelectedItem().equals("Positive Slow")) {
                    speed = 2;
                } else if(speedCombo.getSelectedItem().equals("Negative Slow")) {
                    speed = -2;
                } else if(speedCombo.getSelectedItem().equals("Negative " +
                        "Medium")) {
                    speed = -5;
                } else {
                    speed = -8;
                }

                String strNum = Integer.toString(num);

                ini.put(strNum, "key",
                        Integer.toString((int) ((String) Objects.requireNonNull(
                                mapToComboBox.getSelectedItem())).charAt(0)));

                ini.put(strNum, "action", "2");
                ini.put(strNum, "object", objectComboBox.getSelectedItem());
                ini.put(strNum, "purpose", directionComboBox.getSelectedItem());
                ini.put(strNum, "speed", speed);

                num++;
                strNum = Integer.toString(num);

                ini.put(strNum, "key",
                        Integer.toString((int) ((String) mapToComboBox.getSelectedItem()).charAt(0)));
                ini.put(strNum, "action", "1");
                ini.put(strNum, "object", objectComboBox.getSelectedItem());
                ini.put(strNum, "purpose", directionComboBox.getSelectedItem());
                ini.put(strNum, "speed", speed);

                num++;
                strNum = Integer.toString(num);

                ini.put(strNum, "key",
                        Integer.toString((int) ((String) mapToComboBox.getSelectedItem()).charAt(0)));
                ini.put(strNum, "action", "0");
                ini.put(strNum, "object", objectComboBox.getSelectedItem());
                ini.put(strNum, "purpose", directionComboBox.getSelectedItem());
                ini.put(strNum, "speed", "0");

                ini.store();
            } catch(IOException err) {
                err.printStackTrace();
            }

            // close frame
            frame.dispose();
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}