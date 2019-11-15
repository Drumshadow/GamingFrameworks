package Editor;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddEvents {
    private JPanel panel1;
    private JPanel AddEvents;
    private JComboBox typeComboBox;
    private JSpinner modSpinner;
    private JCheckBox projectileCheckBox;
    private JSpinner timerSpinner;
    private JButton audioSelector;
    private JComboBox<String> object1ComboBox;
    private JComboBox<String> object2ComboBox;
    private JButton saveButton;
    private JComboBox<String> HUDComboBox;
    private File audioFile;

    public AddEvents() {
        audioSelector.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddEvents);
                audioFile = fc.getSelectedFile();
            }
        });

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

                    ini.put(strNum, "type", typeComboBox.getSelectedItem());

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

                    if(projectileCheckBox.isSelected()) {
                        ini.put(strNum, "timer", timerSpinner.getValue());
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Events");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new AddEvents().AddEvents);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            Wini ini = new Wini(new File("./objects/objects.ini"));

            Set<String> keys = ini.keySet();
            String[] values = new String[keys.size() + 1];

            int i = 1;
            for(String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            object1ComboBox = new JComboBox<>(values);
            object2ComboBox = new JComboBox<>(values);

            ini = new Wini(new File("./HUD/HUD.ini"));

            keys = ini.keySet();
            values = new String[keys.size()];

            i = 0;
            for(String key : keys) {
                values[i] = ini.get(key, "name");
                i++;
            }

            HUDComboBox = new JComboBox<>(values);

            SpinnerNumberModel modModel = new SpinnerNumberModel(0, 0, 100, 1);
            modSpinner = new JSpinner(modModel);

            SpinnerNumberModel timerModel = new SpinnerNumberModel(0, 0, 300,
                    5);
            timerSpinner = new JSpinner(timerModel);

        } catch(IOException err) {
            err.printStackTrace();
        }
    }
}
