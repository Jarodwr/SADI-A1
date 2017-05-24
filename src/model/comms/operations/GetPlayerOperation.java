package model.comms.operations;

import model.comms.HostDetails;
import model.interfaces.GameEngine;

public class GetPlayerOperation extends AbstractGameOperation{

	HostDetails host;
	String id;
	
	public GetPlayerOperation(HostDetails host, String id) {
		this.host = host;
		this.id = id;
	}
	
	@Override
	public void execute(GameEngine ge) {
		// TODO Auto-generated method stub
		
	}

}
