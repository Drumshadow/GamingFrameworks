package Editor;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class AddHUDElement {
    private JPanel AddHUDElement;
    private JComboBox<Object> framesComboBox;
    private JButton sprImage;
    private JSpinner xPos;
    private JSpinner yPos;
    private JTextField nameTextField;
    private JCheckBox displayFramesCheckBox;
    private JCheckBox displayHealthCheckBox;
    private JComboBox comboBox1;
    private JCheckBox showScoreCheckBox;
    private JSpinner startSoreSpinner;
    private JSpinner maxScoreSpinner;
    private JButton saveButton;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private File sprPath;

    public AddHUDElement() {
        sprImage.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddHUDElement);
                sprPath = fc.getSelectedFile();
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Wini ini = new Wini(new File("./HUD/HUD.ini"));

                    int i = 0;
                    while(ini.containsKey(Integer.toString(i))) {
                        i++;
                    }

                    String strNum = Integer.toString(i);

                    if(displayFramesCheckBox.isSelected()) {
                        ini.put(strNum, "type", "FrameDisplay");
                    } else if(displayHealthCheckBox.isSelected()) {
                        ini.put(strNum, "type", "HealthBar");
                        ini.put(strNum, "width", widthSpinner.getValue());
                        ini.put(strNum, "height", heightSpinner.getValue());

                        if(sprPath != null) {
                            ini.put(strNum, "spPath",
                                    sprPath.getAbsolutePath().replace('\\',
                                            '/'));
                            ini.put(strNum, "spFrames", "1");
                        }
                    } else if(showScoreCheckBox.isSelected()) {
                        ini.put(strNum, "type", "Score");
                        ini.put(strNum, "score", startSoreSpinner.getValue());
                        ini.put(strNum, "maxScore", maxScoreSpinner.getValue());
                    }
                    ini.put(strNum, "xPos", xPos.getValue());
                    ini.put(strNum, "yPos", yPos.getValue());

                    ini.put(strNum, "name", nameTextField.getText());

                    ini.store();
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        SpinnerNumberModel xPosModel = new SpinnerNumberModel(0, 0, 1920, 1);
        SpinnerNumberModel yPosModel = new SpinnerNumberModel(0, 0, 1080, 1);
        SpinnerNumberModel startScoreModel = new SpinnerNumberModel(0, 0, 10000,
                1);
        SpinnerNumberModel maxScoreModel = new SpinnerNumberModel(0, 0, 10000,
                1);
        SpinnerNumberModel xSizeModel = new SpinnerNumberModel(0, 0, 5, 0.05);
        SpinnerNumberModel ySizeModel = new SpinnerNumberModel(0, 0, 5, 0.05);

        xPos = new JSpinner(xPosModel);
        yPos = new JSpinner(yPosModel);
        startSoreSpinner = new JSpinner(startScoreModel);
        maxScoreSpinner = new JSpinner(maxScoreModel);
        widthSpinner = new JSpinner(xSizeModel);
        heightSpinner = new JSpinner(ySizeModel);
        framesComboBox = new JComboBox<>();
        for(int i = 1; i <= 60; i++) {
            framesComboBox.addItem(i);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add HUD element");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new AddHUDElement().AddHUDElement);
        frame.pack();
        frame.setVisible(true);
    }
}
