package model.comms.operations;

import model.interfaces.GameEngine;

public class CalculateResultOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8165016068056737279L;

	@Override
	public void execute(GameEngine ge) {
		ge.calculateResult();
		
	}

}
