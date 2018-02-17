package gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Frame extends JFrame {

	private SettingsPanel settingspanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		settingspanel = new SettingsPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 359);
		setContentPane(settingspanel);
		setTitle("Photobox 2018");
		setResizable(false);
		ImageIcon img = new ImageIcon("icon.png");
		setIconImage(img.getImage());
	}

}
