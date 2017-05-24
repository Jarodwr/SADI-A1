package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

public class RemoveGameEngineCallbackOperation extends AbstractGameOperation{

	GameEngineCallback gec;
	
	public RemoveGameEngineCallbackOperation(GameEngineCallback gec) {
		this.gec = gec;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
