package Editor.AddOptions;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class AddBackground {
    private JPanel pane;
    private JFrame frame;
    private JButton MusicPath;
    private JButton ArtPath;
    private JButton saveButton;

    private File musicPath;
    private File artPath;

    AddBackground() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 10, 0);

        pane = new JPanel(grid);
        frame = new JFrame();

        JLabel Music = new JLabel("Select Music to Loop in Background (Must end in " +
                ".wav)");
        c.gridy = 0;
        c.gridx = 0;
        pane.add(Music);

        MusicPath = new JButton("Music File");
        c.gridy = 1;
        pane.add(MusicPath, c);

        JLabel Art = new JLabel("Select Art for the Background (Must end in .png)");
        c.gridy = 2;
        pane.add(Art, c);

        ArtPath = new JButton("Art File");
        c.gridy = 3;
        pane.add(ArtPath, c);

        saveButton = new JButton("Save");
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridx = 1;
        pane.add(saveButton, c);

        MusicPath.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getPath().matches(".*\\.wav$");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fc.showOpenDialog(pane);
            musicPath = fc.getSelectedFile();
        });

        ArtPath.addActionListener(e -> {
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
            fc.showOpenDialog(pane);
            artPath = fc.getSelectedFile();
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(pane);
    }

    public void setVisible() {
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}