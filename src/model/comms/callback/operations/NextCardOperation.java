package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class NextCardOperation extends AbstractCallbackOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1641014811600220299L;
	Player player;
	PlayingCard card;
	
	public NextCardOperation(Player player, PlayingCard card) {
		this.player = player;
		this.card = card;
	}
	
	@Override
	public void execute(GameEngineCallback gec) {
		gec.nextCard(player, card, null);
		
	}

}
