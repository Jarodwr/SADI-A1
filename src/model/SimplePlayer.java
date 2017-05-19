package model;

import model.interfaces.Player;

public class SimplePlayer implements Player{
	
	private String id;
	private String name;
	private int points;
	private int bet;
	private int result;
	
	public SimplePlayer(String id, String name, int points) {
		this.id = id;
		this.name = name;
		this.points = points;
	}

	@Override
	public String getPlayerName() {

		return name;
	}

	@Override
	public void setPlayerName(String playerName) {
		
		this.name = playerName;
	}

	@Override
	public int getPoints() {
		
		return points;
	}

	@Override
	public void setPoints(int points) {
		
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		
		return id;
	}

	@Override
	public boolean placeBet(int bet) {
		if (bet <= points) {
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		this.bet = 0;
	}

	@Override
	public int getResult() {
		return this.result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return id + ":" + name + ":" + bet + ":" + points + ":" + result;
	}
}
