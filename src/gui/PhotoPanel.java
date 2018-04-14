package gui;

import at.htlklu.schnittstellen.CharacterEvent;
import at.htlklu.schnittstellen.CharacterListener;
import at.htlklu.schnittstellen.SerielleSchnittstelle;
import threads.LiveView;
import util.PButton;
import util.PhotoTimer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;


public class PhotoPanel extends JPanel implements KeyListener
{

	private final long PRINT_COOLDOWN = 30 * 1000;

	private PhotoFrame photoFrame;
	private LiveView liveview;
	private PhotoTimer phototimer;
	private SettingsPanel settingsPanel;
	private SerielleSchnittstelle serialport;

	private Preferences preferences;
	private Robot robot;
	private Timer timer;

	private int buttonx;
	private int buttony;

	private float eosx;
	private float eosy;

	private String cameraPhotoLocaton;
	private String photoSaveLocation;

	private BufferedImage currentyProcessingImage;

	private BufferedImage printPrompt;
	private BufferedImage overlay;
	private BufferedImage printerBusy;

	//Printing prompt
	private boolean print_request = false;
	private boolean print_pressed = false;

	private boolean updateGraphics = true;

	//Cooldowns
	private long lastPrintTime = 0;


	private boolean m_Free = true;
	private void setFree(boolean free)
	{
		this.m_Free = free;
		serialport.sendByte(m_Free ? 1 : 0);
	}
	public boolean getFree()
	{
		return m_Free;
	}


	private boolean printerReady = true;

	private boolean photoboxReady = true;

	public PhotoPanel(PhotoFrame frame, Preferences prefs, SettingsPanel settingspanel) {
		serialport = new SerielleSchnittstelle(prefs.get("COM", "COM9"));
		serialport.addCharacterListener(new CharacterListener() {
			public void characterReceived(CharacterEvent ev) {
				char rec = (char) ev.getReceivedCharacter();
				if(rec == 't')
				{
					buttonPressed(PButton.TAKE_PHOTO);
				}
				else if(rec == 'n')
				{
					buttonPressed(PButton.YES);
				}
				else if(rec == 'y')
				{
					buttonPressed(PButton.NO);
				}
			}
		});

		frame.addKeyListener(this);
		addKeyListener(this);
		this.photoFrame = frame;
		this.preferences = prefs;
		this.settingsPanel = settingspanel;
		liveview = new LiveView(preferences);
		phototimer = new PhotoTimer(this, 300, 300, 300);

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		try {
			printPrompt = ImageIO.read(new File("res/print.png"));
			overlay = ImageIO.read(new File("res/overlay.png"));
			printerBusy = ImageIO.read(new File("res/printerbusy.png"));
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
		eosx = prefs.getFloat("EOSX", -1);
		eosy = prefs.getFloat("EOSY", -1);
		cameraPhotoLocaton = preferences.get("camerasavelocation", "");
		photoSaveLocation = preferences.get("photosavelocation", "");

		setFree(true);
	}

	@Override
	public void paintComponent(Graphics gr)
	{
		Graphics2D g = (Graphics2D) gr;
		super.paintComponent(g);
		g.drawImage(liveview.currentImage, 0, 0, photoFrame.getWidth(), photoFrame.getHeight(), null);

		phototimer.draw(g);

		if(System.currentTimeMillis() - lastPrintTime > PRINT_COOLDOWN)
		{
			printerReady = true;
		}
		else
		{
			printerReady = false;
		}
	}

	private void takePicture()
	{
		robot.mouseMove(buttonx, buttony);

		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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


	private void buttonPressed(PButton button)
	{
		if(button == PButton.TAKE_PHOTO)
		{
			if(getFree())
			{
				setFree(false);
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
								getGraphics().drawImage(image, 0, 0, photoFrame.getWidth(), photoFrame.getHeight(), null);
								currentyProcessingImage = image;
								timer.schedule(new TimerTask() {
									@Override
									public void run() {
										getGraphics().drawImage(printPrompt, 100, -100, 300, 300, null);
										print_request = true;
									}
								},2000);


							}
						}, 5000);
					}
				});
			}
		}
		else if(button == PButton.YES)
		{
			if(print_request)
			{
				print_pressed = true;
				updateGraphics = true;
				try {
					System.out.println("Saving image to file...");
					System.out.println(currentyProcessingImage.getData().getHeight());
					//currentyProcessingImage.getGraphics().drawImage(overlay, 0, 0, null);
					//printImage(currentyProcessingImage);
					ImageIO.write(currentyProcessingImage, "jpg", new File("C:\\PhotoSaves\\pb2018_img_save_" + System.currentTimeMillis()));
					ImageIO.write(currentyProcessingImage, "jpg", new File(photoSaveLocation + "\\pb2018_img_" + System.currentTimeMillis() + ".jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				finishTakingPicture();
			}
		}
		else if(button == PButton.NO)
		{
			if(print_request &&!print_pressed)
			{
				print_request = false;
				updateGraphics = true;
				setFree(true);

				finishTakingPicture();
			}
		}
	}

	private void finishTakingPicture()
	{
		//highlightEOSUtility();
		//takePicture();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			buttonPressed(PButton.TAKE_PHOTO);
		}
		else if(e.getKeyCode() == KeyEvent.VK_V)
		{
			buttonPressed(PButton.YES);
		}
		else
		{
			buttonPressed(PButton.NO);
		}
	}

	private void printImage(BufferedImage image)
	{
		if(printerReady)
		{
			lastPrintTime = System.currentTimeMillis();

			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(new Printable() {
				public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
					if (pageIndex != 0) {
						return NO_SUCH_PAGE;
					}

					//pageFormat.setOrientation(PageFormat.LANDSCAPE);

					Paper paper = new Paper();

					paper.setSize(4 * 72, 6 * 72);
					pageFormat.setPaper(paper);
					pageFormat.setOrientation(PageFormat.LANDSCAPE);
					

					BufferedImage peter = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_RGB);
					peter.getGraphics().drawImage(image, 0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight(), null);
					try {
						ImageIO.write(peter, "jpg", new File("C:\\Dev\\gas.jpg"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					graphics.drawImage(image, 0, 0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight(), null);
					return PAGE_EXISTS;
				}
			});


			if(printJob.printDialog())
			{
				try {
					printJob.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
			//printJob.cancel();


			setFree(true);
		}
		else
		{
			System.out.println("Printer busy");
			updateGraphics = false;
			getGraphics().drawImage(printerBusy, 0, 0, 1000, 700, null);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			updateGraphics = true;
			setFree(true);
		}
	}

	private void highlightEOSUtility()
	{
		robot.mouseMove((int) eosx, (int) eosy);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public BufferedImage flip(BufferedImage image)
	{
		BufferedImage newimage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i=0;i<image.getWidth();i++) {
			for (int j = 0; j < image.getHeight() / 2; j++) {
				newimage.setRGB(i, j, image.getRGB(i, image.getHeight() - j - 1));
				newimage.setRGB(i, image.getHeight() - j - 1, image.getRGB(i, j));
			}
		}
		return newimage;
	}

	private BufferedImage rotate90DX(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
		return biFlip;
	}

	private BufferedImage merge(BufferedImage a, BufferedImage b)
	{
		BufferedImage result = new BufferedImage(a.getWidth(), a.getHeight(), a.getType());
		result.getGraphics().drawImage(a, 0, 0, null);
		result.getGraphics().drawImage(b, 0, 0, null);
		return result;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
