package view;

import model.comms.GameEngineServerStub;

public class ServerMain {
	
	public static void main(String[] args) {
		GameEngineServerStub gess = new GameEngineServerStub(50001);
		gess.startPolling();

	}
	
}