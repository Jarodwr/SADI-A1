package view;

import model.comms.GameEngineServerStub;

public class ServerMain {
	
	public static void main(String[] args) {
		
		//Start up the server
		GameEngineServerStub gess = new GameEngineServerStub();
		//Let the server start polling
		gess.poll();
	}
}