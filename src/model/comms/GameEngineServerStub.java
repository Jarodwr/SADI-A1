package model.comms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameEngineImpl;
import model.interfaces.GameEngine;

/**
 * This has two tasks, server initialization and connection acceptance
 * @author Jarod
 *
 */
public class GameEngineServerStub {
	private GameEngine ge = new GameEngineImpl();
	
	private ServerSocket server;
	private Socket socket;
	private int port;
	
	/**
	 * @param port	what port the server is listening on
	 */
	public GameEngineServerStub(int port) {
		this.port = port;
		
	}
	
	public void startPolling() {
//		Generate a server which listens on the specified port
		try {
			server = new ServerSocket(port);
			
		} catch(IOException e) {
			e.printStackTrace();
			
		}
		
		try {
//			On connection made, poll for a new connection
			while(true) {
				socket = server.accept();
				new ServerConnectionThread(socket, ge).start();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
}
