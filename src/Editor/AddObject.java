package Editor;

import javax.swing.*;

public class AddObject {
    private JPanel panel1;
    private JComboBox<Integer> framesComboBox;
    private JFrame frame;

    public void display() {
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        frame = new JFrame("Add Object");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        framesComboBox = new JComboBox<>();
        for(int i = 1; i <= 60; i++) {
            framesComboBox.addItem(i);
        }
    }
}
