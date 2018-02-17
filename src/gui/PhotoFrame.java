package gui;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class PhotoFrame extends JFrame
{

    private PhotoPanel settingspanel;

    /**
     * Create the frame.
     */
    public PhotoFrame(Preferences preferences) {
        settingspanel = new PhotoPanel(this, preferences);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);
        setContentPane(settingspanel);
        setTitle("Photobox 2018");
        setResizable(false);
        ImageIcon img = new ImageIcon("icon.png");
        setIconImage(img.getImage());
        //setUndecorated(true);
        //setBackground(new Color(0, 0.2f, 0, 0.5f));
    }

}
