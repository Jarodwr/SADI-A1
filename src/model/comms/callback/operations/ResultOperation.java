package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

public class ResultOperation extends AbstractCallbackOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699379996388185696L;
	Player player;
	int result;
	
	public ResultOperation(Player player, int result) {
		this.player = player;
		this.result = result;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		gui.result(player, result, null);
	}

}
