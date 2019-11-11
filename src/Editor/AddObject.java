package Editor;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AddObject {
    private JPanel AddObject;
    private JComboBox<Integer> framesComboBox;
    private JTextField nameTextField;
    private JButton saveButton;
    private JButton chooseSpriteRequiresAButton;
    private JCheckBox collidesCheckBox;
    private JCheckBox fearsLedgesCheckBox;
    private JSpinner weightSpinner;
    private JSpinner jumpSpinner;
    private JSpinner terminalVelocitySpinner;
    private JComboBox<String> boundingBoxTypeComboBox;
    private JSpinner xPositionSpinner;
    private JSpinner yPositionSpinner;
    private JCheckBox copiesCheckBox;
    private JCheckBox bounceOffWallsCheckBox;
    private JCheckBox bounceOffFloorsCheckBox;
    private JCheckBox movesByItselfCheckBox;
    private JCheckBox shootsProjectileCheckBox;
    private JCheckBox destructsCheckBox;
    private JList<String> destructorsList;
    private File sprite;
    private Wini ini;

    public AddObject() {
        chooseSpriteRequiresAButton.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddObject);
                sprite = fc.getSelectedFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<String> keys = ini.keySet();
                int num = 0;
                while(keys.contains(Integer.toString(num))) {
                    num++;
                }

                String strNum = Integer.toString(num);

                try {
                    ini.put(strNum, "name", nameTextField.getText());
                    ini.put(strNum, "sprPath",
                            sprite.getAbsolutePath().substring(0,
                                    sprite.getAbsolutePath().length() - 6).replace('\\', '/'));
                    ini.put(strNum, "frames", framesComboBox.getSelectedItem());
                    ini.put(strNum, "collide", collidesCheckBox.isSelected());
                    ini.put(strNum, "weight", weightSpinner.getValue());
                    ini.put(strNum, "tv", terminalVelocitySpinner.getValue());
                    ini.put(strNum, "jump", jumpSpinner.getValue());
                    ini.put(strNum, "boxType", Integer.toString(
                            boundingBoxTypeComboBox.getSelectedIndex()));
                    ini.put(strNum, "x", xPositionSpinner.getValue());
                    ini.put(strNum, "y", yPositionSpinner.getValue());

                    String AI = "";

                    if(copiesCheckBox.isSelected()) {
                        AI += "copy";
                    }
                    if(fearsLedgesCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "ledges";
                    }
                    if(bounceOffWallsCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "walls";
                    }
                    if(bounceOffFloorsCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "bounce";
                    }
                    if(movesByItselfCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "auto";
                    }
                    if(shootsProjectileCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "emit";
                    }
                    if(destructsCheckBox.isSelected()) {
                        if(AI.length() != 0) {
                            AI += ",";
                        }
                        AI += "destruct";

                        StringBuilder destroyers = new StringBuilder();
                        for(String value: destructorsList.getSelectedValuesList()) {
                            if(destroyers.length() != 0) {
                                destroyers.append(",");
                            }

                            destroyers.append(value);
                        }

                        ini.put(strNum, "destroyers", destroyers);
                    }

                    if(AI.length() != 0) {
                        ini.put(strNum, "AI", AI);
                    } else {
                        ini.put(strNum, "AI", "null");
                    }

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            ini = new Wini(new File("./objects/objects.ini"));
        } catch(IOException err) {
            err.printStackTrace();
        }

        SpinnerNumberModel weightModel = new SpinnerNumberModel(0, 0, 10, 0.5);
        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);
        SpinnerNumberModel tvModel = new SpinnerNumberModel(0, 0, 30, 1);
        SpinnerNumberModel jumpModel = new SpinnerNumberModel(0, 0, 30, 1);
        weightSpinner = new JSpinner(weightModel);
        xPositionSpinner = new JSpinner(xPosModel);
        yPositionSpinner = new JSpinner(yPosModel);
        terminalVelocitySpinner = new JSpinner(tvModel);
        jumpSpinner = new JSpinner(jumpModel);

        boundingBoxTypeComboBox = new JComboBox<>();
        boundingBoxTypeComboBox.addItem("Rectangle");
        boundingBoxTypeComboBox.addItem("Oval");

        framesComboBox = new JComboBox<>();
        for(int i = 1; i <= 60; i++) {
            framesComboBox.addItem(i);
        }

        Set<String> keys = ini.keySet();
        String[] values = new String[keys.size()];

        int i = 0;
        for(String key : keys) {
            values[i] = ini.get(key, "name");
            i++;
        }

        destructorsList = new JList<>(values);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Object");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new AddObject().AddObject);
        frame.pack();
        frame.setVisible(true);
    }
}
