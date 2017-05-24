package model.comms.operations;

import model.comms.HostDetails;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class RemovePlayerOperation extends AbstractGameOperation{

	HostDetails host;
	Player player;
	
	public RemovePlayerOperation(HostDetails host, Player player) {
		this.player = player;
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
