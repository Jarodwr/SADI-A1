package view;

import java.io.IOException;
import java.net.ServerSocket;

import model.comms.GameEngineServerStub;

public class ServerMain {
	
	public static void main(String[] args) {
		try {
			ServerSocket socket = new ServerSocket(0);
			System.out.println(socket.getLocalSocketAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameEngineServerStub gess = new GameEngineServerStub();
		gess.poll();
	}
}