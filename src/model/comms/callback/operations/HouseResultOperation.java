package model.comms.callback.operations;

import model.interfaces.GameEngineCallback;

public class HouseResultOperation extends AbstractCallbackOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4167978469088100272L;
	int result;
	
	public HouseResultOperation(int result) {
		this.result = result;
	}
	
	@Override
	public void execute(GameEngineCallback gec) {
		gec.houseResult(result, null);
	}

}
