package Editor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class AddEvents {
    private JPanel panel1;
    private JPanel AddEvents;
    private JComboBox typeComboBox;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JTextArea textArea1;
    private JSpinner spinner1;
    private JCheckBox checkBox3;
    private JSpinner spinner2;
    private JButton button1;
    private File audioFile;

    public AddEvents() {
        button1.addActionListener(new ActionListener() {
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
        AddEvents.addComponentListener(new ComponentAdapter() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add HUD element");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new AddEvents().AddEvents);
        frame.pack();
        frame.setVisible(true);
    }
}
