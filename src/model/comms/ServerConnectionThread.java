package model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.comms.operations.AbstractGameOperation;
import model.interfaces.GameEngine;

/**
 * Listens on its new connection and executes operations sent to it
 * @author Jarod
 *
 */
public class ServerConnectionThread extends Thread {
	private Socket socket;
	private GameEngine ge;
	
	public ServerConnectionThread(Socket client, GameEngine ge) {
		this.socket = client;
		this.ge = ge;
	}
	
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(!Thread.currentThread().isInterrupted()) {
				AbstractGameOperation operation = (AbstractGameOperation) ois.readObject();
				operation.execute(ge);
			}
		} catch (IOException | ClassNotFoundException e) {
			return;
		}
	}
}
