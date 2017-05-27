package model.comms.operations;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DealOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8075366754905307264L;
	Player player;
	int delay;
	
	public DealOperation(Player player, int delay) {
		this.player = player;
		this.delay = delay;
	}
	
	@Override
	public void execute(GameEngine ge) {
		if (player != null) {
			ge.dealPlayer(player, delay);
			
		} else {
			ge.dealHouse(delay);
			
		}
		
	}

}
