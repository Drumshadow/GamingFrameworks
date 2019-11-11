package Editor;

import org.ini4j.Wini;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class AddOptions {
    private JPanel panel1;
    private JPanel AddOptions;
    private JLabel Music;
    private JButton MusicPath;
    private JButton ArtPath;
    private JLabel FontPath;
    private JButton Font;
    private JButton saveButton;
    private File fontPath;
    private File artPath;
    private File musicPath;

    public AddOptions() {
        AddOptions.addComponentListener(new ComponentAdapter() {
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
        MusicPath.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddOptions);
                musicPath = fc.getSelectedFile();
            }
        });
        ArtPath.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddOptions);
                artPath = fc.getSelectedFile();
            }
        });
        Font.addActionListener(new ActionListener() {
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
                fc.showOpenDialog(AddOptions);
                fontPath = fc.getSelectedFile();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Wini ini = new Wini(new File("./options/options.ini"));

                    if(artPath != null) {
                        ini.put("background", "art",
                                artPath.getAbsolutePath().replace('\\', '/'));
                    }

                    if(musicPath != null) {
                        ini.put("background", "music",
                                musicPath.getAbsolutePath().replace('\\', '/'));
                    }

                    if(fontPath != null) {
                        ini.put("misc", "font",
                                fontPath.getAbsolutePath().replace('\\', '/'));
                    }

                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Options");
        frame.setContentPane(new AddOptions().AddOptions);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
