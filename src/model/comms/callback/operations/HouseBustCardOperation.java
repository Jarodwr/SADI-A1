package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

public class HouseBustCardOperation extends AbstractCallbackOperation{

	PlayingCard card;
	
	public HouseBustCardOperation(PlayingCard card) {
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		// TODO Auto-generated method stub
		
	}

}