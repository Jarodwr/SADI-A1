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

	//ArrayList<GameEngineCallback> callbacks = new ArrayList<GameEngineCallback>();
	GameEngineCallback singlePlayerCallback = new GameEngineCallbackImpl();
	
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayDeque<PlayingCard> shuffledDeck = new ArrayDeque<PlayingCard>();
	
	PlayingCard[] standardDeck = new PlayingCard[52];
	
	int houseResult = 0;
	
	public GameEngineImpl() {
		
		//Set deck to default values
		PlayingCard.Suit[] suits = PlayingCard.Suit.values();
		PlayingCard.Value[] values = PlayingCard.Value.values();
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < values.length; j++) {
				this.standardDeck[(i*values.length)+j] = new BasicCard(suits[i],values[j]);
			}
		}
		
		shuffledDeck = (ArrayDeque<PlayingCard>) getShuffledDeck();
	}
	
	public boolean gameLoop() {
		
		shuffledDeck = (ArrayDeque<PlayingCard>) getShuffledDeck();
		
		for (Player player : players) {
			dealPlayer(player, 10);
		}
		calculateResult();
		gameLoop();
		return false;
	}
	
	@Override
	public void dealPlayer(Player player, int delay) {
		int result = 0;
		int prevResult = 0;
		
		do {
			prevResult = result;
			PlayingCard card = shuffledDeck.removeFirst();
			singlePlayerCallback.nextCard(player, card, this);
			if (result + card.getScore() < 21) {
				result += card.getScore();
			} else {
				
				singlePlayerCallback.bustCard(player, card, this);
			}
		} while (result != prevResult);
		singlePlayerCallback.result(player, result, this);
		player.setResult(result);
	}

	@Override
	public void dealHouse(int delay) {
		// TODO Auto-generated method stub
		int result = 0;
		int prevResult = 0;
		
		do {
			prevResult = result;
			PlayingCard card = shuffledDeck.removeFirst();
			singlePlayerCallback.nextHouseCard(card, this);
			
			if (result + card.getScore() < 21) {
				result += card.getScore();
			} else {
				
				singlePlayerCallback.houseBustCard(card, this);
			}
		} while (result != prevResult);
		singlePlayerCallback.houseResult(result, this);
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
		return players.remove(player);
	}

	@Override
	public void calculateResult() {
		dealHouse(10);
		
		for (Player player : players) {
			int pr = player.getResult();
			
			if (pr > houseResult) {
				player.setPoints(player.getPoints() + 2 * player.getBet());
			}
			else if (pr == houseResult) {
				player.setPoints(player.getPoints() + player.getBet());
			}
			player.resetBet();
		}
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// TODO Auto-generated method stub
		
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
		
		for (PlayingCard card : temp) {
			shuffled.add(card);
		}
		
		return shuffled;
	}
}
