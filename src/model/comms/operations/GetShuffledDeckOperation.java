package model.comms.operations;

import model.comms.HostDetails;
import model.interfaces.GameEngine;

public class GetShuffledDeckOperation extends AbstractGameOperation{

	HostDetails host;
	
	public GetShuffledDeckOperation(HostDetails host) {
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
