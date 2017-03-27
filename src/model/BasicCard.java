package model;

import model.interfaces.PlayingCard;

public class BasicCard implements PlayingCard{

	private Suit suit;
	private Value value;
	
	BasicCard(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
	}
	
	@Override
	public Suit getSuit() {
		// TODO Auto-generated method stub
		return suit;
	}

	@Override
	public Value getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public int getScore() {
		PlayingCard.Value[] allValues = PlayingCard.Value.values();
		for (int i = 0; i < allValues.length; i++) {
			if (value == allValues[i]) {
				if (i < 10) {
					return i+1;
				}
				else {
					return 10;
				}
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return suit.toString() + ":" + value.toString();
	}
}
