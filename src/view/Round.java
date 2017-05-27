package view;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import model.interfaces.Player;
import model.interfaces.PlayingCard;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class Round extends JPanel {

	private ClientGui main;
	
	private JPanel houseCardSprites = new JPanel();
	private JPanel yourCardSprites = new JPanel();
	
	private JLabel cardLabel = new JLabel("");
	private JLabel cardImage = new JLabel();
	private JButton btnResults;
	private JPanel board = new JPanel();
	
	public Round(ClientGui main) {
		
		this.main = main;
		
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane dealer = new JSplitPane();
		add(dealer, BorderLayout.NORTH);
		
		dealer.setRightComponent(houseCardSprites);
		houseCardSprites.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblDealersCards = new JLabel("Dealer's Cards");
		lblDealersCards.setBorder(new EmptyBorder(20,20,20,20));
		dealer.setLeftComponent(lblDealersCards);
		
		JSplitPane player = new JSplitPane();
		add(player, BorderLayout.SOUTH);
		
		player.setRightComponent(yourCardSprites);
		yourCardSprites.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblYourCards = new JLabel("Your Cards    ");
		lblYourCards.setBorder(new EmptyBorder(20,20,20,20));
		player.setLeftComponent(lblYourCards);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnResults = new JButton("Start Game");
		panel.add(btnResults, BorderLayout.CENTER);
		btnResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnResults.getText().equals("Results")){
					main.page("results");
					resetView();	//Reset board
					
				}
				else if (btnResults.getText().equals("Start Game")) {
					btnResults.setText("Results");
					btnResults.setEnabled(false);
					//Start round on new thread to avoid locking up the GUI
					new Thread() {
						public void run() {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									main.getController().round();
									
								}
								
							});
							
						}
						
					}.start();
					
				}
				
			}
			
		});
		
		add(board, BorderLayout.CENTER);
		board.setLayout(new BorderLayout(0, 0));
		
		cardImage = new JLabel();
		cardImage.setHorizontalAlignment(SwingConstants.CENTER);
		board.add(cardImage);
		cardLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		board.add(cardLabel, BorderLayout.SOUTH);
		cardLabel.setHorizontalAlignment(SwingConstants.CENTER);

	}
	
	public Player getCurrentPlayer() {
		return main.getCurrentPlayer();
		
	}
	
	public void setCurrentPlayer(Player p) {
		main.setCurrentPlayer(p);
		
	}
	
	public void setPlayers() {
		
	}
	
	/**
	 * Updates the GUI with the latest card drawn
	 * @param playerId	Determines where to put the card
	 * @param card	Card being played
	 */
	public void displayCard(String playerId, PlayingCard card) {
		
		String filename = "";	//Sprite filename
		switch (card.getSuit()) {
		case Hearts: 
			filename += "h";
		break;
		case Spades: 
			filename += "s";
		break;
		case Clubs: 
			filename += "c";
		break;
		case Diamonds: 
			filename += "d";
		break;
		
		}
		
		switch (card.getValue()) {
		case Ace: 
			filename += "A";
		break;
		case Two: 
			filename += "2";
		break;
		case Three: 
			filename += "3";
		break;
		case Four: 
			filename += "4";
		break;
		case Five: 
			filename += "5";
		break;
		case Six: 
			filename += "6";
		break;
		case Seven: 
			filename += "7";
		break;
		case Eight: 
			filename += "8";
		break;
		case Nine: 
			filename += "9";
		break;
		case Ten: 
			filename += "10";
		break;
		case Jack: 
			filename += "J";
		break;
		case Queen: 
			filename += "Q";
		break;
		case King: 
			filename += "K";
		break;
		
		}
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(".\\sprites\\" + filename + ".png"));
			
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
		if (image != null) {
			cardImage.setIcon(new ImageIcon(resizeCardSprite(image, 2)));
		
		}
		
		cardLabel.setText(filename);
		if (playerId != null) {
			if (main.getCurrentPlayer() != null && playerId.equals(main.getCurrentPlayer().getPlayerId())) {
				JLabel c = new JLabel();
				c.setIcon(new ImageIcon(resizeCardSprite(image, 0.5)));
				yourCardSprites.add(c);
				
			}
			
		} else {
			JLabel c = new JLabel();
			c.setIcon(new ImageIcon(resizeCardSprite(image, 0.5)));
			houseCardSprites.add(c);
			
		}
		
		btnResults.setText("Results");
		btnResults.setEnabled(false);
		repaint();
		
	}
	
	/**
	 * 
	 * @param player	Player in focus
	 * @param result	Current result value
	 */
	public void result(Player player, int result) {
		String message;
		if (player == null) {
			message = "House ";
			btnResults.setEnabled(true);
			main.setDealerResult(result);
			
		} else {
			message = player.getPlayerId() + ", " + player.getPlayerName() + ", ";
			
		}
		JOptionPane.showMessageDialog(this, message + " result: " + result + ".");	//	Need to specify which player it's the result of
		
	}
	
	/**
	 * image resizer
	 * @param image
	 * @param scale
	 * @return
	 */
	private BufferedImage resizeCardSprite(BufferedImage image, double scale) {
		BufferedImage newImg = new BufferedImage((int) (image.getWidth() * scale), (int) (image.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		return new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR).filter(image, newImg);
	}
	
	/**
	 * Remove all unnecessary elements and reset board to original state
	 */
	private void resetView() {
		yourCardSprites.removeAll();
		houseCardSprites.removeAll();
		btnResults.setText("Start Game");
		cardImage.setIcon(null);
		cardLabel.setText("");
		
	}
	
}
