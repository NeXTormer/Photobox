package gui;

import threads.LiveView;
import util.PhotoTimer;

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
	private PhotoTimer phototimer;

	private Preferences preferences;
	private Robot robot;
	private Timer timer;

	private int buttonx;
	private int buttony;

	private String cameraPhotoLocaton;
	private String photoSaveLocation;

	private BufferedImage currentyProcessingImage;

	private BufferedImage printPrompt;

	//Printing prompt
	private boolean print_request = false;
	private boolean print_pressed = false;

	private boolean updateGraphics = true;

	public PhotoPanel(PhotoFrame frame, Preferences prefs) {
		frame.addKeyListener(this);
		addKeyListener(this);
		this.photoFrame = frame;
		this.preferences = prefs;
		liveview = new LiveView(preferences);
		phototimer = new PhotoTimer(this, 300, 300, 300);

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		try {
			printPrompt = ImageIO.read(new File("res/print.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(updateGraphics)
				{
					repaint();
				}
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

		phototimer.draw(g);
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
			phototimer.runTimer(new Runnable() {
				@Override
				public void run() {
					System.out.println("Timer Finished");
					takePicture();

					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							BufferedImage image = loadImageFromCamera();
							updateGraphics = false;
							getGraphics().drawImage(image, 0, 0, 1280, 720, null);
							currentyProcessingImage = image;
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									getGraphics().drawImage(printPrompt, 100, -100, 300, 300, null);
									//TODO: Get Arduino button input
									print_request = true;
									timer.schedule(new TimerTask() {
										@Override
										public void run() {
											//check if the print button has already been pressed --> if not: reset to normal mode
											if(print_pressed == false)
											{
												print_request = false;
												updateGraphics = true;
											}
										}
									}, 3000);
								}
							},2000);

						}
					}, 1000);
				}
			});



		}
		//TODO: arduino button press to print
		else
		{
			if(print_request)
			{
				print_pressed = true;
				updateGraphics = true;
				try {
					System.out.println("Saving image to file...");
					System.out.println(currentyProcessingImage.getData().getHeight());
					ImageIO.write(currentyProcessingImage, "jpg", new File(photoSaveLocation + "\\pb2018_img_" + System.currentTimeMillis() + ".jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}