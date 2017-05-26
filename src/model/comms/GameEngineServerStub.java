package model.comms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameEngineImpl;
import model.interfaces.GameEngine;

public class GameEngineServerStub {
	private GameEngine ge = new GameEngineImpl();
	
	private ServerSocket server;
	private Socket socket;
	
	public GameEngineServerStub() {
		try {
			server = new ServerSocket(50000);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(true) {
				socket = server.accept();
				new ServerConnectionThread(socket, ge).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
