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
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard(player.getPlayerId(), card);
					System.out.println("ID: " + player.getPlayerId() + ", Name: " + player.getPlayerName() + ", Card: " + card);
				}
			});
		
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard("-1", card);
				}
			});
		
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.result(player, result);
				}
			});
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard(null, card);
					System.out.println("House, Card: " + card);
				}
			});
		
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.displayCard("-1", card);
				}
			});
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		if (this.gui != null)
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
					gui.result(null, result);
				}
			});
		
	}

}
