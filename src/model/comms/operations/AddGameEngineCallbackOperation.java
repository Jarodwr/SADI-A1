package model.comms.operations;

import model.comms.HostDetails;
import model.comms.ServerStubGameEngineCallback;
import model.interfaces.GameEngine;

public class AddGameEngineCallbackOperation extends AbstractGameOperation{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2843286608238531125L;
	HostDetails host;
	
	public AddGameEngineCallbackOperation(HostDetails host) {
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		ge.addGameEngineCallback(new ServerStubGameEngineCallback(host));
	}

}
