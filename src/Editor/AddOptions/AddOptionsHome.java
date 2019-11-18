package Editor.AddOptions;

import javax.swing.*;
import java.awt.*;

public class AddOptionsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addBackground;

    public AddOptionsHome() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        addBackground = new JButton("Add Background Picture and Music");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(addBackground, c);

        addBackground.addActionListener(e -> new AddBackground().setVisible());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}