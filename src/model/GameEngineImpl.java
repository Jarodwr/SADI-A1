package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class GameEngineImpl implements GameEngine {

	private ArrayList<GameEngineCallback> callbacks = new ArrayList<GameEngineCallback>();
	private PlayingCard[] standardDeck = new PlayingCard[52];
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayDeque<PlayingCard> currentDeck = new ArrayDeque<PlayingCard>();

	private int houseResult = 0;

	/**
	 * Sets up the shuffled deck, makes it easier to
	 */
	public GameEngineImpl() {

		PlayingCard.Suit[] suits = PlayingCard.Suit.values();
		PlayingCard.Value[] values = PlayingCard.Value.values();
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < values.length; j++) {
				this.standardDeck[(i * values.length) + j] = new PlayingCardImpl(suits[i], values[j]);
				
			}
			
		}
		currentDeck = (ArrayDeque<PlayingCard>) getShuffledDeck();

	}

	@Override
	public void dealPlayer(Player player, int delay) {
		int result = 0;
		int prevResult = 0;
		player = getPlayer(player.getPlayerId());
		
//		draw cards while the player has not reached the point cap
		do {
			prevResult = result;
			PlayingCard card = currentDeck.removeFirst();

			try {
				Thread.sleep(delay);

			} catch (InterruptedException e) {
				e.printStackTrace();

			}
//			Check that the player has not busted
			if (result + card.getScore() <= 21) {
				for (GameEngineCallback c : callbacks) {
					c.nextCard(player, card, this);

				}
//				update result
				result += card.getScore();

			} else {
				for (GameEngineCallback c : callbacks) {
					c.bustCard(player, card, this);

				}
			}

		} while (result != prevResult);
		
		for (GameEngineCallback c : callbacks) {
			player.setResult(result); // update result in player
			c.result(player, result, this);

		}

	}

	@Override
	public void dealHouse(int delay) {
		int result = 0;
		int prevResult = 0;

//		keep dealing while not busted
		do {
			prevResult = result;
			PlayingCard card = currentDeck.removeFirst();

			try {
				Thread.sleep(delay);

			} catch (InterruptedException e) {
				e.printStackTrace();

			}
//			check for bust
			if (result + card.getScore() <= 21) {
				for (GameEngineCallback c : callbacks) {
					c.nextHouseCard(card, this);

				}
				result += card.getScore();

			} else {
				for (GameEngineCallback c : callbacks) {
					c.houseBustCard(card, this);

				}

			}

		} while (result != prevResult);

		for (GameEngineCallback c : callbacks) {
			c.houseResult(result, this);
			
		}
		houseResult = result;

	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);

	}

	@Override
	public Player getPlayer(String id) {
		for (Player player : players) {
			if (id.equals(player.getPlayerId())) {
				return player;
				
			}
			
		}
		return null;

	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(getPlayer(player.getPlayerId()));

	}

	@Override
	public void calculateResult() {
		// post round post results screen
		for (Player player : players) {
			// Set points
			if (player.getResult() > houseResult) {
				player.setPoints(player.getPoints() + player.getBet());

			} else if (player.getResult() < houseResult) {
				player.setPoints(player.getPoints() - player.getBet());

			}
			//Reset player bet and result
			player.resetBet();
			player.setResult(0);

		}
		//Reset deck post round
		currentDeck = (ArrayDeque<PlayingCard>) getShuffledDeck();
		
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacks.add(gameEngineCallback);

	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		for (GameEngineCallback gec : callbacks) {
			if (gec.equals(gameEngineCallback)) {
				callbacks.remove(gec);
				return;
			}

		}

	}

	@Override
	public Collection<Player> getAllPlayers() {
		return players;
		
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		return getPlayer(player.getPlayerId()).placeBet(bet);
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {

		PlayingCard[] temp = standardDeck.clone();
		Deque<PlayingCard> shuffled = new ArrayDeque<PlayingCard>();

		Collections.shuffle(Arrays.asList(temp));

		for (PlayingCard card : temp) {
			shuffled.add(card);
		}

		return shuffled;
	}
	
}
