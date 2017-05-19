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
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GuiClient extends JFrame {
	
	private GameController c = new GameController();
	private Player currentPlayer = null;
	
	private CardLayout cards = new CardLayout();
	
	private Menu start = new Menu(this);
	private AddPlayer addPlayer = new AddPlayer(this);
	private PlaceBet placeBet = new PlaceBet(this);
	private Round round = new Round(this);
	private Results results = new Results(this);
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int dealerResult = 0;
	
	public GuiClient() {
		
		getContentPane().setLayout(cards);
		
		//Add all pages to the cardlayout
		getContentPane().add(start, "start");
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
		return c;
	}
	
	public void page(String s) {
		switch(s) {
		case "start":
			if (currentPlayer != null)
				start.canStart(true);
			break;
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
			results.addResults(players, dealerResult);
			results.announceResult();
			break;
		}
		cards.show(this.getContentPane(), s);
	}
	
	public Round getRound() {
		return this.round;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void setCurrentPlayer(Player p) {
		this.currentPlayer = p;
	}

	public void addRoundPlayer(Player player) {
		players.add(player);
	}
	
	public void setDealerResult(int d) {
		this.dealerResult = d;
	}
	
	/**
	 * reset client side round variables
	 */
	public void resetRound() {
		dealerResult = 0;
		players.removeAll(players);
	}
	
}
