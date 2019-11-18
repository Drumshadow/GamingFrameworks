package Editor.AddOptions;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class AddFont {
    private JPanel pane;
    private JFrame frame;
    private JButton Font;
    private JButton saveButton;

    private File fontPath;

    public AddFont() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel FontPath = new JLabel("Select a Font file of type .ttf");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(FontPath, c);

        Font = new JButton("Font File");
        c.gridy = 1;
        pane.add(Font, c);

        Font.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getPath().matches(".*\\.ttf$");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fc.showOpenDialog(pane);
            fontPath = fc.getSelectedFile();
        });
    }
}
