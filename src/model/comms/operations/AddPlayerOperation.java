package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class AddPlayerOperation extends AbstractGameOperation{

	Player player;
	
	public AddPlayerOperation(Player player) {
		this.player = player;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
