package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DealOperation extends AbstractGameOperation{

	Player player;
	int delay;
	
	public DealOperation(Player player, int delay) {
		this.player = player;
		this.delay = delay;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
