package Editor;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddInputs {
    private JPanel panel1;
    private JPanel AddInputs;
    private JComboBox buttonPurposeDropDown;
    private JComboBox mapToComboBox;
    private JComboBox controllerTypeComboBox;
    private JComboBox<String> objectComboBox;
    private JSpinner speedSpinner;
    private JButton audioFilleIfNeededButton;
    private JComboBox actionComboBox;
    private JButton saveButton;
    private File audioPath;

    public AddInputs() {
        audioFilleIfNeededButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getPath().matches(".*\\.png$");
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }
                });
                fc.showOpenDialog(AddInputs);
                audioPath = fc.getSelectedFile();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Wini ini = new Wini(new File("./inputs/keyboard.ini"));
                    int num = 0;

                    while(ini.containsKey(Integer.toString(num))) {
                        num++;
                    }

                    String strNum = Integer.toString(num);

                    ini.put(strNum, "action",
                            buttonPurposeDropDown.getSelectedIndex());

                    if(mapToComboBox.getSelectedIndex() == 36) {
                        ini.put(strNum, "key", 32);
                    } else if(mapToComboBox.getSelectedIndex() < 26) {
                        ini.put(strNum, "key",
                                mapToComboBox.getSelectedIndex() + 65);
                    } else {
                        ini.put(strNum, "key",
                                mapToComboBox.getSelectedIndex() + 22);
                    }

                    ini.put(strNum, "object", objectComboBox.getSelectedItem());
                    ini.put(strNum, "purpose",
                            buttonPurposeDropDown.getSelectedItem());
                    ini.put(strNum, "speed", speedSpinner.getValue());
                    if(audioPath != null) {
                        ini.put(strNum, "audio",
                                audioPath.getAbsolutePath().replace('\\', '/'));
                    }

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Slammin' Game Editor");
        frame.setContentPane(new AddInputs().AddInputs);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        SpinnerNumberModel speedModel = new SpinnerNumberModel(0, -20.0, 20.0,
                0.5);
        speedSpinner = new JSpinner(speedModel);

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
    }
}
