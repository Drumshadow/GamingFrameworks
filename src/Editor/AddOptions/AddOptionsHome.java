package Editor.AddOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddOptionsHome {
    private JPanel pane;
    private JFrame frame;
    private JButton addBackground;
    private JButton addTextFont;

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

        addTextFont = new JButton("Add a Text Font");
        c.gridy = 1;
        pane.add(addTextFont, c);

        addBackground.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBackground().setVisible();
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(250, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
