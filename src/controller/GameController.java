package controller;

import java.util.Collection;

import model.GameEngineCallbackImpl;
import model.SimplePlayer;
import model.comms.GameEngineClientStub;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.Round;

public class GameController {
	
	final GameEngine gameEngine = new GameEngineClientStub();
	
	/**
	 * 
	 * @param name	player name
	 * @param points	amount of points in the bank
	 * @return	player that is created, if no player is created return null
	 */
	public Player addPlayer(String name, int points) {
		if (name.length() > 0 && points > 0) {
			int latestId = -1;	//Get a fresh ID
			for (Player p : gameEngine.getAllPlayers()) {
				int c = Integer.parseInt(p.getPlayerId());
				if (c > latestId) {
					latestId = c;
					
				}
				
			}
			gameEngine.addPlayer(new SimplePlayer(Integer.toString(latestId+1), name, points));
			return gameEngine.getPlayer(Integer.toString(latestId+1));
			
		}
		return null;
		
	}
	
	/**
	 * 
	 * @param id	id of the player making the bet
	 * @param bet	amount
	 * @return	Success
	 */
	public boolean makeBet(String id, int bet) {
		Player p = gameEngine.getPlayer(id);
		if (p != null) {
			return gameEngine.placeBet(p, bet);
			
		}
		return false;
	}
	
	/**
	 * Sets up the game
	 * @param gameGui
	 */
	public void preGame(Round gameGui) {
		Collection<Player> p = gameEngine.getAllPlayers();
		Player[] players = new Player[p.size()];
		p.toArray(players);
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl(gameGui));
	}
	
	/**
	 * Round logic
	 */
	public void round() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int dt = 1000;	//	Delay time
				for (Player player : gameEngine.getAllPlayers()) {
					gameEngine.dealPlayer(player, dt);
					
				}
				gameEngine.dealHouse(dt);
				
			}
			
		}).start();
	}
	
	/**
	 * fix up post round
	 */
	public void postRound() {
		gameEngine.calculateResult();
		
	}
	
	public boolean loadPlayers() {
		return false;
		
	}
	
	public boolean savePlayers() {
		return false;
		
	}
	
	public void removePlayer() {
		
	}
	
	public Player getPlayerbyId(String id) {
		return gameEngine.getPlayer(id);
		
	}
}
