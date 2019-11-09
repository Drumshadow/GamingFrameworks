package Editor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private File sprite;

    public AddObject() {
        chooseSpriteRequiresAButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
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
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Object");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new AddObject().AddObject);
        frame.pack();
        frame.setVisible(true);
    }
}
