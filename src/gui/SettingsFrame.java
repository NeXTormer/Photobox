package gui;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class SettingsFrame extends JFrame {

	private SettingsPanel settingspanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame settingsFrame = new SettingsFrame();
					settingsFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		settingspanel = new SettingsPanel(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 467, 388);
		setContentPane(settingspanel);
		setTitle("Photobox 2018");
		setResizable(false);
		ImageIcon img = new ImageIcon("icon.png");
		setIconImage(img.getImage());
		//setUndecorated(true);
		//setBackground(new Color(0, 0.2f, 0, 0.5f));
	}

}
