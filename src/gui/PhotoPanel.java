package gui;

import threads.LiveView;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

public class PhotoPanel extends JPanel implements KeyListener {

	private PhotoFrame photoFrame;
	private LiveView liveview;
	private Preferences preferences;
	private Robot robot;

	private int buttonx;
	private int buttony;

	private String cameraPhotoLocaton;
	private String photoSaveLocation;

	public PhotoPanel(PhotoFrame frame, Preferences prefs) {
		frame.addKeyListener(this);
		addKeyListener(this);
		this.photoFrame = frame;
		this.preferences = prefs;
		liveview = new LiveView(preferences);

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 32, 32);

		buttonx = preferences.getInt("buttonx", -1);
		buttony = preferences.getInt("buttony", -1);
		cameraPhotoLocaton = preferences.get("camerasavelocation", "");
		photoSaveLocation = preferences.get("photosavelocation", "");
	}

	@Override
	public void paintComponent(Graphics gr)
	{
		Graphics2D g = (Graphics2D) gr;
		super.paintComponent(g);

		g.drawImage(liveview.currentImage, 0, 0, 1280, 720, null);

	}

	private void takePicture()
	{
		robot.mouseMove(buttonx, buttony);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private BufferedImage loadImageFromCamera()
	{
		File folder = new File(cameraPhotoLocaton);
		ArrayList<String> files = new ArrayList<>();
		for(File f : folder.listFiles())
		{
			if(f.isFile())
			{
				files.add(f.getName());
			}
		}
		Collections.sort(files);
		String latest = files.get(files.size() - 1);
		String latestpath = folder.getAbsolutePath() + "\\" + latest;
		System.out.println(latestpath);

		try {
			BufferedImage image = ImageIO.read(new File(latestpath));
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}


		return null;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 32)
		{
			System.out.println("Take Picture");
			takePicture();
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			loadImageFromCamera();

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
