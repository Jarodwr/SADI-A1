package view;

import model.interfaces.Player;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class AddPlayer extends JPanel {
	
	private JTextField name;
	private JTextField points;
	
	public AddPlayer(ClientGui main) {
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		panel.setBorder(new EmptyBorder(40,40,40,40));
		
		JLabel lblName = new JLabel("Name: ");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel.add(lblName, gbc_lblName);
		
		name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(name, gbc_textField);
		name.setColumns(10);
		
		JLabel lblAddress = new JLabel("Points: ");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 1;
		panel.add(lblAddress, gbc_lblAddress);
		
		points = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(points, gbc_textField_1);
		points.setColumns(10);
		
		JButton btnSubmit = new JButton("Start");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 2;
		panel.add(btnSubmit, gbc_btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.getText().length() > 0 && points.getText().length() > 0) {	//Check input format
					try {
						Integer.parseInt(points.getText());
						Player np = main.getController().addPlayer(name.getText(), Integer.parseInt(points.getText()));
						
						if (np == null) {
							JOptionPane.showMessageDialog(main, "Could not add player");
						} else {
							JOptionPane.showMessageDialog(main, "Player successfully added");
							if (main.getCurrentPlayer() == null) {
								main.setCurrentPlayer(np);	//if there is no current player, this player is the new current player
								main.getController().preGame(main.getRound());
								main.page("place bet");
							}
						}
					} catch(NumberFormatException exception) {
						JOptionPane.showMessageDialog(main, "points format is incorrect");
					}
				} else {
					JOptionPane.showMessageDialog(main, "Could not add player");
				}
			}
		});
	}

}
