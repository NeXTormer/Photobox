package gui;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class PhotoFrame extends JFrame
{

    public PhotoPanel photopanel;

    /**
     * Create the frame.
     */
    public PhotoFrame(Preferences preferences, SettingsPanel settingspanel) {
        photopanel = new PhotoPanel(this, preferences, settingspanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);
        setContentPane(photopanel);
        setTitle("Photobox 2018");
        setResizable(false);
        ImageIcon img = new ImageIcon("icon.png");
        setIconImage(img.getImage());
        //setUndecorated(true);
        //setBackground(new Color(0, 0.2f, 0, 0.5f));
    }

}
