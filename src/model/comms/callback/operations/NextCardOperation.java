package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class NextCardOperation extends AbstractCallbackOperation{

	Player player;
	PlayingCard card;
	
	public NextCardOperation(Player player, PlayingCard card) {
		this.player = player;
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		// TODO Auto-generated method stub
		
	}

}