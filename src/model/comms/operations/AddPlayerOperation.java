package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class AddPlayerOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583884800945004868L;
	Player player;
	
	public AddPlayerOperation(Player player) {
		this.player = player;
	}
	
	@Override
	public void execute(GameEngine ge) {
		ge.addPlayer(player);
	}

}
