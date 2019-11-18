package Editor.AddInputs;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddSound {
    private JPanel pane;
    private JFrame frame;
    private JLabel keyMapLabel;
    private JComboBox<String> mapToComboBox;
    private JLabel audioFileLabel;
    private JButton audioFile;
    private JButton saveButton;
    private File audioPath;

    public AddSound() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        keyMapLabel = new JLabel("Choose Key to Map Movement To");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(keyMapLabel, c);

        mapToComboBox = new JComboBox<>();

        for(int i = 48; i <= 90; i++) {
            if(i != 58 && i != 60 && !(i < 65 && i > 61)) {
                mapToComboBox.addItem(Integer.toString(i));
            }
        }

        c.gridy = 1;
        pane.add(mapToComboBox, c);

        audioFileLabel = new JLabel("Audio File to Play One Key Press");
        c.gridy = 2;
        pane.add(audioFileLabel, c);

        audioFile = new JButton("Audio File");
        c.gridy = 3;
        pane.add(audioFile, c);

        saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 4;
        c.gridx = 1;
        pane.add(saveButton, c);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
