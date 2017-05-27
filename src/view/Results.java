package view;

import javax.swing.JOptionPane;

import model.interfaces.Player;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Results extends JPanel {

	private int dealerResult = 0;
	private ClientGui main;
	private JPanel resultBoard = new JPanel();
	
	public Results(ClientGui main) {
		this.main = main;
		setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Next Round");
		add(btnNewButton, BorderLayout.SOUTH);
		
		add(resultBoard, BorderLayout.CENTER);
		resultBoard.setLayout(new GridLayout(0, 1, 0, 0));
		resultBoard.setBorder(new EmptyBorder(40,40,40,40));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.getController().getPlayerbyId(main.getCurrentPlayer().getPlayerId());
				resultBoard.removeAll();
				repaint();
				main.resetRound();
				main.page("place bet");
			}
		});
	}

	/**
	 * currently unused
	 */
	public void announceResult() {
		
		Player cp = main.getCurrentPlayer();
		String message;
		String newBalance;
		if (dealerResult < cp.getResult()){
			message = "You won!";
			newBalance = Integer.toString(cp.getPoints() + cp.getBet());
			
		} else if (dealerResult > main.getCurrentPlayer().getResult()) {
			message = "You lost...";
			newBalance = Integer.toString(cp.getPoints() - cp.getBet());
			
		} else {
			message = "It's a draw!";
			newBalance = Integer.toString(cp.getPoints());
		}
		
		message += "\nCurrent balance: " + newBalance;
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * Adds results to the GUI
	 * @param players	list of players being viewed
	 * @param dealerResult	dealer result to compare to
	 */
	public void addResults(ArrayList<Player> players, int dealerResult) {
		this.dealerResult = dealerResult;
		for (Player p : players) {
			System.out.println(p.getResult() + " | " + dealerResult);
			int r = p.getResult();
			String color = "black";
			System.out.println(r < dealerResult);
			if (r < dealerResult)
				color = "red";
			else if (r > dealerResult)
				color = "green";
			JLabel j = new JLabel("<html><font color='" + color + "'>" + p.getPlayerId() + " : " + p.getPlayerName() + "</font></html>");
			resultBoard.add(j, BorderLayout.CENTER);
		}
	}
}
