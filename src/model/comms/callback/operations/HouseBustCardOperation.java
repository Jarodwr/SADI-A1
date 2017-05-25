package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

public class HouseBustCardOperation extends AbstractCallbackOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8800738462879316126L;
	PlayingCard card;
	
	public HouseBustCardOperation(PlayingCard card) {
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		gui.houseBustCard(card, null);
	}

}
