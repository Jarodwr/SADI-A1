package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class BustCardOperation extends AbstractCallbackOperation{

	Player player;
	PlayingCard card;
	
	public BustCardOperation(Player player, PlayingCard card) {
		this.player = player;
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		// TODO Auto-generated method stub
		
	}

}
