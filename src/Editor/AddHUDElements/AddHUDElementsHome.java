package Editor.AddHUDElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddHUDElementsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addHealthHUD;
    private JButton addFPSDisplay;
    private JButton addScoreDisplay;

    public AddHUDElementsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        addHealthHUD = new JButton("Add Health Indicator");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addHealthHUD, c);

        addFPSDisplay = new JButton("Display FPS");
        c.gridy = 1;
        pane.add(addFPSDisplay, c);

        addScoreDisplay = new JButton("Add Score Display");
        c.gridy = 2;
        pane.add(addScoreDisplay, c);

        addHealthHUD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddHealth().setVisible();
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
