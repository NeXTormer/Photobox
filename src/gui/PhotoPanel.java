package gui;

import threads.LiveView;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class PhotoPanel extends JPanel {

	private PhotoFrame photoFrame;
	private LiveView liveview;
	private Preferences preferences;

	public PhotoPanel(PhotoFrame frame, Preferences prefs) {
		this.photoFrame = frame;
		this.preferences = prefs;
		liveview = new LiveView(preferences);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 32, 32);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(liveview.currentImage, 0, 0, 1280, 720, null);

	}
}
