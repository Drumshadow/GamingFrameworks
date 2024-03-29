package Editor.AddInputs;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class AddSound {
    private JPanel pane;
    private JFrame frame;
    private JComboBox<String> mapToComboBox;
    private JComboBox<String> objectComboBox;
    private File audioPath;

    AddSound() {
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

        JLabel objectLabel = new JLabel("Select the object to apply the move to");
        c.gridy = 1;
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
            err.printStackTrace();
        }

        c.gridy = 2;
        pane.add(objectComboBox, c);

        c.gridy = 3;
        pane.add(mapToComboBox, c);

        JLabel audioFileLabel = new JLabel("Audio File to Play On Key Press");
        c.gridy = 4;
        pane.add(audioFileLabel, c);

        JButton audioFile = new JButton("Audio File");
        c.gridy = 5;
        pane.add(audioFile, c);

        JButton saveButton = new JButton("Save");
        c.gridwidth = 1;
        c.gridy = 6;
        c.gridx = 1;
        pane.add(saveButton, c);

        audioFile.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getPath().matches(".*\\.ogg$");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fc.showOpenDialog(pane);
            audioPath = fc.getSelectedFile();
        });

        saveButton.addActionListener(e -> {
            try {
                Wini ini = new Wini(new File("./inputs/keyboard.ini"));
                int num = 0;

                while(ini.containsKey(Integer.toString(num))) {
                    num++;
                }

                String strNum = Integer.toString(num);

                ini.put(strNum, "action", "0");

                ini.put(strNum, "key",
                        Integer.toString((int) ((String) Objects.requireNonNull(mapToComboBox.getSelectedItem())).charAt(0)));

                ini.put(strNum, "object", objectComboBox.getSelectedItem());
                ini.put(strNum, "purpose", "PlaySound");

                if(audioPath != null) {
                    ini.put(strNum, "audio",
                            audioPath.getAbsolutePath().replace('\\', '/'));
                }

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
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}