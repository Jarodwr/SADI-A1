package model;

import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.Round;

public class GameEngineCallbackImpl implements GameEngineCallback{

	private Round gui = null;
	
	public GameEngineCallbackImpl() {

	}
	
	public GameEngineCallbackImpl(Round gui) {
		this.gui = gui;
	}
	
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard(player.getPlayerId(), card);

				}
				
			});
		
		}
		
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard("-1", card);

				}
				
			});
			
		}
		
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					updateCurrentPlayer(player);
					player.setResult(result);

				}
				
			});
		
		}
		
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard(null, card);
					System.out.println("House, Card: " + card);
					
				}
			});
			
		}
		
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard("-1", card);
					
				}
				
			});
			
		}
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		if (this.gui != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.result(null, result);
					
				}
				
			});
			
		}
		
	}
	
	private void updateCurrentPlayer(Player player) {
		if (gui.getCurrentPlayer().getPlayerId().equals(player.getPlayerId())) {
			System.out.println("Player updated: " + player.getResult() + "|" + gui.getCurrentPlayer().getResult());
			gui.setCurrentPlayer(player);
			
		}
		
	}

}
