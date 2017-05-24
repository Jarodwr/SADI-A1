package model.comms.operations;

import model.interfaces.GameEngine;

public class CalculateResultOperation extends AbstractGameOperation{

	@Override
	public void execute(GameEngine ge) {
		ge.calculateResult();
		
	}

}
