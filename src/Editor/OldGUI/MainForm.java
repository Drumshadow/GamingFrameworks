package Editor.OldGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm {
    private JButton addInputsButton;
    private JPanel EditorHome;
    private JButton addHUDElementsButton;
    private JButton addObjectButton;
    private JButton addOptionsButton;
    private JButton AddEvents;

    public MainForm() {
        addObjectButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AddObject.main(null);
            }
        });

        addHUDElementsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {AddHUDElement.main(null);
            }
        });

        addInputsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddInput.main(null);
            }
        });

        addOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOption.main(null);
            }
        });

        AddEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEvent.main(null);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slammin' Game Editor");
        frame.setContentPane(new MainForm().EditorHome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
