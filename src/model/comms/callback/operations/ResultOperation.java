package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

public class ResultOperation extends AbstractCallbackOperation{

	Player player;
	int result;
	
	public ResultOperation(Player player, int result) {
		this.player = player;
		this.result = result;
	}
	
	@Override
	public void execute(GameEngineCallback gui) {
		// TODO Auto-generated method stub
		
	}

}
