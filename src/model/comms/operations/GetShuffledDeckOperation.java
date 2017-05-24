package model.comms.operations;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.comms.HostDetails;
import model.interfaces.GameEngine;

public class GetShuffledDeckOperation extends AbstractGameOperation{

	HostDetails host;
	
	public GetShuffledDeckOperation(HostDetails host) {
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		try {
			Socket socket = new Socket(host.getHostname(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(ge.getShuffledDeck());
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
