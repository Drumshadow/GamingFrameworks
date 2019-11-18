package Editor.AddHUDElements;

import org.ini4j.Wini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddFPS {
    private JPanel pane;
    private JFrame frame;
    private JTextField nameTextField;
    private JButton saveButton;

    AddFPS() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel nameLabel = new JLabel("Name of the fps display");
        c.gridx = 0;
        c.gridy = 0;
        pane.add(nameLabel, c);

        nameTextField = new JTextField();
        c.gridy = 1;
        pane.add(nameTextField, c);

        saveButton = new JButton("Save");
        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 1;
        pane.add(saveButton, c);

        saveButton.addActionListener(e -> {
            try {
                Wini ini = new Wini(new File("./HUD/HUD.ini"));

                int i = 0;
                while(ini.containsKey(Integer.toString(i))) {
                    i++;
                }

                String strNum = Integer.toString(i);

                ini.put(strNum, "type", "FrameDisplay");
                ini.put(strNum, "xPos", 50);
                ini.put(strNum, "yPos", 950);

                ini.put(strNum, "name", nameTextField.getText());

                ini.store();
            } catch(IOException err) {
                System.out.println(err.getMessage());
            }

            // close frame
            frame.dispose();
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
