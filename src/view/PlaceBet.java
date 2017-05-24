package view;

import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PlaceBet extends JPanel {
	private JTextField textField;
	private JLabel lblBank = new JLabel();
	private ClientGui main;
	
	public PlaceBet(ClientGui m) {
		
		this.main = m;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblBank = new JLabel("Bank: 0");
		GridBagConstraints gbc_lblBank = new GridBagConstraints();
		gbc_lblBank.anchor = GridBagConstraints.WEST;
		gbc_lblBank.insets = new Insets(0, 0, 5, 0);
		gbc_lblBank.gridx = 1;
		gbc_lblBank.gridy = 0;
		add(lblBank, gbc_lblBank);
		
		JLabel lblAmount = new JLabel("Amount: ");
		GridBagConstraints gbc_lblAmount = new GridBagConstraints();
		gbc_lblAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmount.anchor = GridBagConstraints.EAST;
		gbc_lblAmount.gridx = 0;
		gbc_lblAmount.gridy = 1;
		add(lblAmount, gbc_lblAmount);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnPlaceBet = new JButton("place bet");
		GridBagConstraints gbc_btnPlaceBet = new GridBagConstraints();
		gbc_btnPlaceBet.anchor = GridBagConstraints.EAST;
		gbc_btnPlaceBet.gridx = 1;
		gbc_btnPlaceBet.gridy = 2;
		add(btnPlaceBet, gbc_btnPlaceBet);
		btnPlaceBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String betStr = textField.getText();
				if (betStr.length() > 0) {
					try {
						int bet = Integer.parseInt(betStr);
						if (main.getController().makeBet(main.getCurrentPlayer().getPlayerId(), bet)) {
							main.page("round");
						} else {
							JOptionPane.showMessageDialog(main, "Could not place bet, user doesn't have enough points");
						}
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(main, "Could not place bet, please enter points in the correct format");
					}
				} else {
					JOptionPane.showMessageDialog(main, "No bet placed");
					main.page("round");
				}

			}
		});
	}
	
	public void updateBank() {
		System.out.println(main.getCurrentPlayer());
		if (main.getCurrentPlayer() != null)
			this.lblBank.setText("Bank: " + main.getCurrentPlayer().getPoints()); 
	}
}
