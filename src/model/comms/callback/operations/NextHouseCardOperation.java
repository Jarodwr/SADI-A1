package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

public class NextHouseCardOperation extends AbstractCallbackOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392769043225804275L;
	PlayingCard card;
	
	public NextHouseCardOperation(PlayingCard card) {
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		gui.nextHouseCard(card, null);
	}

}
