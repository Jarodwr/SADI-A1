package controller;

import java.util.ArrayList;
import java.util.Collection;

import model.GameEngineCallbackImpl;
import model.SimplePlayer;
import model.comms.GameEngineClientStub;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.Round;

public class GameController {

	final GameEngine ge = new GameEngineClientStub();

	/**
	 * 
	 * @param name player name
	 * @param points amount of points in the bank
	 * @return player that is created, if no player is created return null
	 */
	public Player addPlayer(String name, int points) {
		if (name.length() > 0 && points > 0) {
			// Get a fresh ID
			int latestId = -1;
			for (Player player : ge.getAllPlayers()) {
				int c = Integer.parseInt(player.getPlayerId());
				if (c > latestId) {
					latestId = c;

				}

			}
			ge.addPlayer(new SimplePlayer(Integer.toString(latestId + 1), name, points));
			return ge.getPlayer(Integer.toString(latestId + 1));

		}
		return null;

	}

	/**
	 * 
	 * @param id	id of the player making the bet
	 * @param bet	amount
	 * @return Success
	 */
	public boolean makeBet(String id, int bet) {
		Player p = ge.getPlayer(id);
		if (p != null) {
			return ge.placeBet(p, bet);

		}
		return false;
	}

	/**
	 * Sets up the game
	 * 
	 * @param gameGui
	 */
	public void preGame(Round gameGui) {
		Collection<Player> p = ge.getAllPlayers();
		Player[] players = new Player[p.size()];
		p.toArray(players);
		ge.addGameEngineCallback(new GameEngineCallbackImpl(gameGui));
	}

	/**
	 * Round logic
	 */
	public void round() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int dt = 1000; // Delay time
				for (Player player : ge.getAllPlayers()) {
					if (player.getBet() > 0) {
						ge.dealPlayer(player, dt);

					}

				}
				ge.dealHouse(dt);

			}

		}).start();

	}

	/**
	 * fix up post round
	 */
	public void postRound() {
		ge.calculateResult();

	}

	public boolean loadPlayers() {
		return false;

	}

	public boolean savePlayers() {
		return false;

	}

	public void removePlayer(Player player) {
		ge.removePlayer(player);

	}

	public Player getPlayerbyId(String id) {
		return ge.getPlayer(id);

	}

	public ArrayList<Player> getAllPlayers() {
		return new ArrayList<Player>(ge.getAllPlayers());
	}
}
