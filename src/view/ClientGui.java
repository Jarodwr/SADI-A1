package view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.GameController;
import model.interfaces.Player;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5561312846222115498L;
	private GameController controller = new GameController();
	private Player currentPlayer = null;
	
	private CardLayout cards = new CardLayout();
	
	private AddPlayer addPlayer = new AddPlayer(this);
	private PlaceBet placeBet = new PlaceBet(this);
	private Round round = new Round(this);
	private Results results = new Results(this);
	
	private int dealerResult = 0;
	
	public ClientGui() {
		getContentPane().setLayout(cards);
		
		//Add all pages to the cardlayout
		getContentPane().add(addPlayer, "add player");
		getContentPane().add(placeBet, "place bet");
		getContentPane().add(round, "round");
		getContentPane().add(results, "results");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSavePlayerList = new JMenuItem("Save player list");
		mnFile.add(mntmSavePlayerList);
		mntmSavePlayerList.setEnabled(false);
		
		JMenuItem mntmLoadPlayerList = new JMenuItem("Load player list");
		mnFile.add(mntmLoadPlayerList);
		mntmLoadPlayerList.setEnabled(false);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
			
		});
		
		setSize(new Dimension(700,500));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	public GameController getController() {
		return controller;
		
	}
	
	public void page(String s) {
		switch(s) {
		case "add player":
			break;
		case "place bet":
			placeBet.updateBank();
			placeBet.repaint();
			break;
		case "round":
			round.repaint();
			break;
		case "results":
			results.addResults(controller.getAllPlayers(), dealerResult);
			results.announceResult();
			if (currentPlayer.getResult() > dealerResult) {
				currentPlayer.setPoints(currentPlayer.getPoints() + currentPlayer.getBet());
				
			}
			else if (currentPlayer.getResult() < dealerResult) {
				currentPlayer.setPoints(currentPlayer.getPoints() - currentPlayer.getBet());
				
			}
			currentPlayer.resetBet();
			currentPlayer.setResult(0);
			controller.postRound();
			break;
			
		}
		cards.show(this.getContentPane(), s);
		
	}
	
	public Round getRound() {
		return this.round;
		
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
		
	}
	
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
		
	}
	
	public void setDealerResult(int dealerResult) {
		this.dealerResult = dealerResult;
		
	}

	public boolean makeBet(int bet) {
		if (currentPlayer.placeBet(bet)) {
			return controller.makeBet(currentPlayer.getPlayerId(), bet);
			
		}
		return false;
	}
	
}
