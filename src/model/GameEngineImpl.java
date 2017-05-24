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

public class GameEngineImpl implements GameEngine{

	ArrayList<GameEngineCallback> callbacks = new ArrayList<GameEngineCallback>();	//Stores callbacks
	
	ArrayList<Player> players = new ArrayList<Player>();	//Stores players
	ArrayDeque<PlayingCard> shuffledDeck = new ArrayDeque<PlayingCard>();	//Stores deck
	
	PlayingCard[] standardDeck = new PlayingCard[52];	//	Base deck
	
	int houseResult = 0;
	
	/**
	 * Sets up the shuffled deck, makes it easier to
	 */
	public GameEngineImpl() {
		
		//Set deck
		PlayingCard.Suit[] suits = PlayingCard.Suit.values();
		PlayingCard.Value[] values = PlayingCard.Value.values();
		for (int i = 0; i < suits.length; i++)
			for (int j = 0; j < values.length; j++)
				this.standardDeck[(i*values.length)+j] = new PlayingCardImpl(suits[i],values[j]);

		shuffledDeck = (ArrayDeque<PlayingCard>) getShuffledDeck();
	}
	
	@Override
	public void dealPlayer(Player player, int delay) {
		int result = 0;
		int prevResult = 0;
		do {
			prevResult = result;
			PlayingCard card = shuffledDeck.removeFirst();
			
			try {
				Thread.sleep(delay);	//delay
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			if (result + card.getScore() <= 21) {	//Check that the player has not busted
				for (GameEngineCallback c : callbacks)
					c.nextCard(player, card, this);
				
				result += card.getScore();	//update result
			} else {
				for (GameEngineCallback c : callbacks)
					c.bustCard(player, card, this);
			}
			
		} while (result != prevResult);	//Keep drawing cards while the player has not reached the point cap
		
		for (GameEngineCallback c : callbacks) {
			c.result(player, result, this);
			System.out.println(player.getPlayerName());
		}
		
		player.setResult(result);	//update result in player
	}

	@Override
	public void dealHouse(int delay) {
		int result = 0;
		int prevResult = 0;
		
		do {
			prevResult = result;
			PlayingCard card = shuffledDeck.removeFirst();

			try {
				Thread.sleep(delay);	//delay
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
			if (result + card.getScore() <= 21) {	//check for bust
				for (GameEngineCallback c : callbacks)
					c.nextHouseCard(card, this);
				result += card.getScore();
			} else {
				for (GameEngineCallback c : callbacks)
					c.houseBustCard(card, this);
			}

		} while (result != prevResult);	//keep dealing while not busted
		
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
		for (Player player : players)
			if (id.equals(player.getPlayerId()))
				return player;

		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(player);
		
	}

	@Override
	public void calculateResult() {
		//post round post results screen
		for (Player player : players) {
			int pr = player.getResult();
			
			//Set points
			if (pr > houseResult)
				player.setPoints(player.getPoints() + player.getBet());
			else if (pr < houseResult)
				player.setPoints(player.getPoints() - player.getBet());
				
			player.resetBet();	//reset bet amount
			player.setResult(0);	//reset result amount
		}
		shuffledDeck = (ArrayDeque<PlayingCard>) getShuffledDeck(); //reset deck
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacks.add(gameEngineCallback);
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacks.remove(gameEngineCallback);
		
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return players;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		return player.placeBet(bet);
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		
		PlayingCard[] temp = this.standardDeck.clone();
		Deque<PlayingCard> shuffled = new ArrayDeque<PlayingCard>();
		
		Collections.shuffle(Arrays.asList(temp));
		
		for (PlayingCard card : temp)
			shuffled.add(card);
		
		return shuffled;
	}
}
