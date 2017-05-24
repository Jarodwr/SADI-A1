package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

public class AddGameEngineCallbackOperation extends AbstractGameOperation{
	GameEngineCallback gec;
	
	public AddGameEngineCallbackOperation(GameEngineCallback gec) {
		this.gec = gec;
	}
	
	//TODO: This might require host details
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
