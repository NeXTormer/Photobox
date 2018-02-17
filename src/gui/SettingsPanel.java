package gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SettingsPanel extends JPanel {
	private JTextField tf_cameraLocation;
	private JTextField tf_saveLocation;

	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblNewLabel = new JLabel("Photobox 2018");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		
		JButton btn_Start = new JButton("Start");
		btn_Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_Start.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		
		JButton btn_SetButtonPosition = new JButton("Set Button Position");
		btn_SetButtonPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_SetButtonPosition.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblcFelixHolz = new JLabel("(c) Felix Holz, 2018");
		lblcFelixHolz.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		
		JButton btn_SetConstraints = new JButton("Set Preview Constraints");
		btn_SetConstraints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_SetConstraints.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblButtonPos = new JLabel("X: 1000 Y: 1000");
		lblButtonPos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblButtonPos.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblConstraintSize = new JLabel("X: 1000 Y: 1000 Width: 1000 Height: 1000");
		lblConstraintSize.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConstraintSize.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel("Camera Photo Location");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
		tf_cameraLocation = new JTextField();
		tf_cameraLocation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		tf_cameraLocation.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Photo Save Location");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		
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
			}
		});
		
		JButton btn_selectCameraLocation = new JButton("...");
		btn_selectCameraLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
							.addComponent(lblcFelixHolz))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btn_SetButtonPosition, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btn_SetConstraints, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblConstraintSize, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
								.addComponent(lblButtonPos, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(tf_saveLocation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
								.addComponent(tf_cameraLocation, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_selectSaveLocation, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_selectCameraLocation, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn_Start, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_2)))
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
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(btn_Start, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
