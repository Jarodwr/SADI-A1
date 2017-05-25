package model.comms.operations;

import model.comms.HostDetails;
import model.comms.ServerStubGameEngineCallback;
import model.interfaces.GameEngine;

public class RemoveGameEngineCallbackOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1803133792349815203L;
	HostDetails host;
	
	public RemoveGameEngineCallbackOperation(HostDetails host) {
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		ge.removeGameEngineCallback(new ServerStubGameEngineCallback(host));
		
	}

}
