package model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameEngineImpl;
import model.comms.operations.AbstractGameOperation;
import model.interfaces.GameEngine;

public class GameEngineServerStub {
	private GameEngine ge = new GameEngineImpl();
	
	private ServerSocket server;
	private Socket socket;
	private ObjectInputStream ois;
	
	public GameEngineServerStub() {
		try {
			server = new ServerSocket(50000);
			socket = server.accept();
			ois = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void poll() {
		while(true) {
			try {
				AbstractGameOperation operation = (AbstractGameOperation) ois.readObject();
				operation.execute(ge);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
