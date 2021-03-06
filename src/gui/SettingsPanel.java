package gui;

import at.htlklu.schnittstellen.SerielleSchnittstelle;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import at.htlklu.schnittstellen.CharacterListener;
import at.htlklu.schnittstellen.CharacterEvent;

public class SettingsPanel extends JPanel {

	private JTextField tf_cameraLocation;
	private JTextField tf_saveLocation;
	private JLabel lblComPort;
	private JLabel lbleos;


	private Preferences preferences;
	private JFrame frame;
	private SettingsPanel settingsPanel;

	/**
	 * Create the panel.
	 */
	public SettingsPanel(JFrame frame) {
		this.frame = frame;
		settingsPanel = this;
		preferences = Preferences.userNodeForPackage(this.getClass());


		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Photobox 2018");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		
		JButton btn_Start = new JButton("Start");
		btn_Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PhotoFrame pf = new PhotoFrame(preferences, settingsPanel);
				pf.setVisible(true);
				frame.setVisible(false);

			}
		});
		btn_Start.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		

		JSeparator separator = new JSeparator();
		
		JLabel lblcFelixHolz = new JLabel("(c) Felix Holz, 2018");
		lblcFelixHolz.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		

		JLabel lblButtonPos = new JLabel("X: 1000 Y: 1000");
		lblButtonPos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblButtonPos.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblConstraintSize = new JLabel("X: 1000 Y: 1000 Width: 1000 Height: 1000");
		lblConstraintSize.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConstraintSize.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel("Camera Photo Location");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));


		JButton btn_SetConstraints = new JButton("Set Preview Constraints");
		btn_SetConstraints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TimeUnit.SECONDS.sleep(3);
					playSound("confirm.wav");
					Point m1 = MouseInfo.getPointerInfo().getLocation();
					TimeUnit.SECONDS.sleep(3);
					playSound("confirm.wav");

					Point m2 = MouseInfo.getPointerInfo().getLocation();

					int x = m1.x;
					int y = m1.y;
					int width = m2.x - x;
					int height = m2.y - y;

					lblConstraintSize.setText("X: " + x + " Y: " + y + " Width: " + width + " Height: " + height);
					preferences.putInt("conx", x);
					preferences.putInt("cony", y);
					preferences.putInt("conw", width);
					preferences.putInt("conh", height);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_SetConstraints.setFont(new Font("Century Gothic", Font.PLAIN, 12));



		tf_cameraLocation = new JTextField();
		tf_cameraLocation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		tf_cameraLocation.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Photo Save Location");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		JButton btn_SetButtonPosition = new JButton("Set PButton Position");
		btn_SetButtonPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TimeUnit.SECONDS.sleep(3);
					playSound("confirm.wav");
					PointerInfo mouse = MouseInfo.getPointerInfo();
					int x = mouse.getLocation().x;
					int y = mouse.getLocation().y;

					preferences.putInt("buttonx", x);
					preferences.putInt("buttony", y);

					lblButtonPos.setText("X: " + x + " Y: " + y);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
		});
		btn_SetButtonPosition.setFont(new Font("Century Gothic", Font.PLAIN, 12));



		tf_saveLocation = new JTextField();
		tf_saveLocation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		tf_saveLocation.setColumns(10);
		
		JButton btn_selectSaveLocation = new JButton("...");
		btn_selectSaveLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
				fc.setDialogTitle("Select photo save location...");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fc.showOpenDialog(btn_selectSaveLocation) == JFileChooser.APPROVE_OPTION)
				{
					System.out.println(fc.getSelectedFile().getAbsolutePath());
					tf_saveLocation.setText(fc.getSelectedFile().getAbsolutePath());
					preferences.put("photosavelocation", fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		JButton btn_selectCameraLocation = new JButton("...");
		btn_selectCameraLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
				fc.setDialogTitle("Select camera save location...");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fc.showOpenDialog(btn_selectCameraLocation) == JFileChooser.APPROVE_OPTION)
				{
					System.out.println(fc.getSelectedFile().getAbsolutePath());
					tf_cameraLocation.setText(fc.getSelectedFile().getAbsolutePath());
					preferences.put("camerasavelocation", fc.getSelectedFile().getAbsolutePath());
				}
			}
		});

		lblComPort = new JLabel("Dr. Werner Schubb");
		lblComPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComPort.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JButton button = new JButton("Select COM Port");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preferences.put("COM", SerielleSchnittstelle.listAndSelectPort());
				try {
					preferences.flush();
				} catch (BackingStoreException e1) {
					e1.printStackTrace();
				}
				lblComPort.setText("COM-Port: " + preferences.get("COM", "Undefined"));
			}
		});
		button.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JButton btnSetEosPosition = new JButton("Set EOS Position");
		btnSetEosPosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				playSound("confirm.wav");
				Point m1 = MouseInfo.getPointerInfo().getLocation();

				preferences.putFloat("EOSX", m1.x);
				preferences.putFloat("EOSY", m1.y);

				lbleos.setText("X: " + m1.x + ", Y: " + m1.y);

				try {
					preferences.flush();
				} catch (BackingStoreException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnSetEosPosition.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		lbleos = new JLabel("X: 0, Y: 0");
		lbleos.setHorizontalAlignment(SwingConstants.RIGHT);
		lbleos.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
							.addComponent(lblcFelixHolz))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btn_SetButtonPosition, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_SetConstraints, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblConstraintSize, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(lblButtonPos, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel_2)
										.addComponent(btnSetEosPosition, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
									.addComponent(lbleos, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(tf_saveLocation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
										.addComponent(tf_cameraLocation, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_selectCameraLocation, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_selectSaveLocation, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn_Start, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(lblComPort, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblcFelixHolz))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_SetButtonPosition)
						.addComponent(lblButtonPos))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_SetConstraints)
						.addComponent(lblConstraintSize))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblComPort, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSetEosPosition)
						.addComponent(lbleos))
					.addGap(19)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_cameraLocation, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_selectCameraLocation))
					.addGap(18)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_saveLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_selectSaveLocation))
					.addGap(18)
					.addComponent(btn_Start, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);

		tf_cameraLocation.setText(preferences.get("camerasavelocation", ""));
		tf_saveLocation.setText(preferences.get("photosavelocation", ""));

		lblButtonPos.setText("X: " + preferences.getInt("buttonx", -1) + " Y: " + preferences.getInt("buttony", -1));
		lblConstraintSize.setText("X: " + preferences.getInt("conx", -1) + " Y: " + preferences.getInt("cony", -1) + " Width: " + preferences.getInt("conw", -1) + " Height: " + preferences.getInt("conh", -1));
		lblComPort.setText("COM-Port: " + preferences.get("COM", "Undefined"));


		lbleos.setText("X: " + (int) preferences.getFloat("EOSX", -1) + ", Y: " + (int) preferences.getFloat("EOSY", -1));


	}

	public void playSound(String name)
	{
		try {
			// Open an audio input stream.
			File soundFile = new File(name); //you could also get the sound file with an URL
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
}
